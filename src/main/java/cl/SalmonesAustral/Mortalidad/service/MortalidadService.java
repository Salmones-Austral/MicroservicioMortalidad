package cl.SalmonesAustral.Mortalidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.SalmonesAustral.Mortalidad.modelo.Mortalidad;
import cl.SalmonesAustral.Mortalidad.repository.MortalidadRepository;

@Service
public class MortalidadService {

    private final MortalidadRepository repository;
    private final WebClient webClient;

    public MortalidadService(
            MortalidadRepository repository,
            @Qualifier("mortalidadWebClient") WebClient webClient) {

        this.repository = repository;
        this.webClient = webClient;
    }

    public List<Mortalidad> getAll() {
        return repository.findAll();
    }

    public Mortalidad save(Mortalidad m) {
        return repository.save(m);
    }

    public List<Mortalidad> getByJaula(int jaulaId) {
        return repository.findByJaulaId(jaulaId);
    }

    // lógica clave del negocio
    public double calcularPromedio(int jaulaId) {

        List<Mortalidad> lista = repository.findByJaulaId(jaulaId);

        if (lista.isEmpty()) return 0;

        double suma = 0;

        for (Mortalidad m : lista) {
            suma += m.getPorcentaje();
        }

        return suma / lista.size();
    }

    public String obtenerDatosExternos() {
        return webClient.get()
                .uri("/estadisticas")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
