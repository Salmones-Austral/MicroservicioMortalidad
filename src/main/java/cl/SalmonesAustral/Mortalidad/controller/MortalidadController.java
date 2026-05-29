package cl.SalmonesAustral.Mortalidad.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.SalmonesAustral.Mortalidad.modelo.Mortalidad;
import cl.SalmonesAustral.Mortalidad.service.MortalidadService;

@RestController
@RequestMapping("/api/mortalidad")
public class MortalidadController {

    private final MortalidadService service;

    public MortalidadController(MortalidadService service) {
        this.service = service;
    }

    @GetMapping
    public List<Mortalidad> listar() {
        return service.getAll();
    }

    @PostMapping
    public Mortalidad guardar(@RequestBody Mortalidad m) {
        return service.save(m);
    }

    @GetMapping("/jaula/{id}")
    public ResponseEntity<Double> promedio(@PathVariable int id) {
        double promedio = service.calcularPromedio(id);
        return ResponseEntity.ok(promedio);
    }
}