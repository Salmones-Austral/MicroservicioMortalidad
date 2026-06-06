package cl.SalmonesAustral.Mortalidad.service;

import java.util.List;
import org.springframework.stereotype.Service;

import cl.SalmonesAustral.Mortalidad.modelo.Mortalidad;
import cl.SalmonesAustral.Mortalidad.repository.MortalidadRepository;
import cl.SalmonesAustral.Mortalidad.client.JaulaClient; // Importamos el nuevo Client

@Service
public class MortalidadService {

    private final MortalidadRepository repository;
    private final JaulaClient jaulaClient; // Usamos el Client en lugar del WebClient crudo

    // Inyectamos el Repository y el Client
    public MortalidadService(MortalidadRepository repository, JaulaClient jaulaClient) {
        this.repository = repository;
        this.jaulaClient = jaulaClient;
    }

    public List<Mortalidad> getAll() {
        return repository.findAll();
    }

    public Mortalidad save(Mortalidad m) {
        // 1. Validar usando nuestro Client ordenado
        if (!jaulaClient.existeJaula(m.getJaulaId())) {
            throw new IllegalArgumentException("La Jaula con ID " + m.getJaulaId() + " no existe. No se puede registrar mortalidad.");
        }

        // 2. Guardar el registro
        Mortalidad guardada = repository.save(m);

        // 3. Regla de negocio (Historia de Usuario 1)
        evaluarAlertaMortalidad(m.getJaulaId());

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

    private void evaluarAlertaMortalidad(int jaulaId) {
        double promedioActual = calcularPromedio(jaulaId);
        
        if (promedioActual > 0.15) {
            System.out.println("⚠️ ALERTA CRÍTICA ⚠️");
            System.out.println("La Jaula " + jaulaId + " ha superado el 0.15% de mortalidad (Promedio actual: " + promedioActual + "%).");
        }
    }
}