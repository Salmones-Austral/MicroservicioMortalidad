package cl.SalmonesAustral.Mortalidad.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.SalmonesAustral.Mortalidad.modelo.Mortalidad;
import cl.SalmonesAustral.Mortalidad.service.MortalidadService;
import cl.SalmonesAustral.Mortalidad.dto.CreateMortalidadRequest;
import cl.SalmonesAustral.Mortalidad.mapper.MortalidadMapper;

@RestController
@RequestMapping("/api/v1/mortalidad") // Le agregué v1 para mantener estándar
public class MortalidadController {

    private final MortalidadService service;

    public MortalidadController(MortalidadService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Mortalidad>> listar() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody CreateMortalidadRequest request) {
        Mortalidad m = MortalidadMapper.toModel(request);
        Mortalidad nueva = service.save(m);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @GetMapping("/jaula/{id}/promedio")
    public ResponseEntity<Double> promedio(@PathVariable int id) {
        double promedio = service.calcularPromedio(id);
        return ResponseEntity.ok(promedio);
    }
}