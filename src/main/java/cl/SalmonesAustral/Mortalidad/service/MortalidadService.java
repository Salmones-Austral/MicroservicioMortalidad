package cl.SalmonesAustral.Mortalidad.service;

import java.util.List;
import org.springframework.stereotype.Service;

import cl.SalmonesAustral.Mortalidad.modelo.Mortalidad;
import cl.SalmonesAustral.Mortalidad.repository.MortalidadRepository;
import cl.SalmonesAustral.Mortalidad.client.JaulaClient;
import cl.SalmonesAustral.Mortalidad.client.AlertaClient;

@Service
public class MortalidadService {

    private final MortalidadRepository repository;
    private final JaulaClient jaulaClient;
    private final AlertaClient alertaClient;

    public MortalidadService(MortalidadRepository repository, JaulaClient jaulaClient, AlertaClient alertaClient) {
        this.repository = repository;
        this.jaulaClient = jaulaClient;
        this.alertaClient = alertaClient;
    }

    public List<Mortalidad> getAll() {
        return repository.findAll();
    }

    public Mortalidad save(Mortalidad m) {
        if (!jaulaClient.existeJaula(m.getJaulaId())) {
            throw new IllegalArgumentException("La Jaula con ID " + m.getJaulaId() + " no existe. No se puede registrar mortalidad.");
        }

        Mortalidad guardada = repository.save(m);

        // OPCIÓN A: Evaluamos usando directamente el porcentaje que se acaba de registrar
        evaluarAlertaMortalidad(guardada.getId(), guardada.getJaulaId(), guardada.getPorcentaje());

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

    // Modificado para recibir el porcentaje actual ingresado en el POST
    private void evaluarAlertaMortalidad(Integer mortalidadId, int jaulaId, double porcentajeActual) {
        
        // Compara directamente el valor del input actual (ej: 0.18 o 0.25)
        if (porcentajeActual >= 0.15) { 
            System.out.println("⚠️ Mortalidad alta detectada (" + porcentajeActual + "%). Llamando al microservicio de Alertas...");
            alertaClient.notificarAlerta(mortalidadId, jaulaId, porcentajeActual);
        }
    }
}