package cl.SalmonesAustral.Mortalidad.service;

import java.util.List;
import org.springframework.stereotype.Service;

import cl.SalmonesAustral.Mortalidad.modelo.Mortalidad;
import cl.SalmonesAustral.Mortalidad.repository.MortalidadRepository;
import cl.SalmonesAustral.Mortalidad.client.JaulaClient;
import cl.SalmonesAustral.Mortalidad.client.AlertaClient; // IMPORTANTE

@Service
public class MortalidadService {

    private final MortalidadRepository repository;
    private final JaulaClient jaulaClient;
    private final AlertaClient alertaClient; // Agregamos el cliente

    public MortalidadService(MortalidadRepository repository, JaulaClient jaulaClient, AlertaClient alertaClient) {
        this.repository = repository;
        this.jaulaClient = jaulaClient;
        this.alertaClient = alertaClient; // Inyectado
    }

    public List<Mortalidad> getAll() {
        return repository.findAll();
    }

    public Mortalidad save(Mortalidad m) {
        if (!jaulaClient.existeJaula(m.getJaulaId())) {
            throw new IllegalArgumentException("La Jaula con ID " + m.getJaulaId() + " no existe. No se puede registrar mortalidad.");
        }

        Mortalidad guardada = repository.save(m);

        // Pasamos el ID de la mortalidad recién guardada a la evaluación
        evaluarAlertaMortalidad(guardada.getId(), m.getJaulaId());

        return guardada;
    }

    public double calcularPromedio(int jaulaId) {
        List<Mortalidad> lista = repository.findByJaulaId(jaulaId);
        if (lista.isEmpty()) return 0;
        
        double suma = 0;
        for (Mortalidad m : lista) {
            suma += m.getPorcentaje();
        }
        return suma / lista.size();
    }

    private void evaluarAlertaMortalidad(Integer mortalidadId, int jaulaId) {
        double promedioActual = calcularPromedio(jaulaId);
        
        // CORREGIDO: Ahora gatilla si el promedio es igual o superior a 0.15%
        if (promedioActual >= 0.15) { 
            System.out.println("⚠️ Mortalidad alta detectada (" + promedioActual + "%). Llamando al microservicio de Alertas...");
            alertaClient.notificarAlerta(mortalidadId, jaulaId, promedioActual);
        }
    }
}