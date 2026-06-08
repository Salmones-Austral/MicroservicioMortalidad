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
@RestController                                                                  // Identifica la clase como un componente controlador de Spring para APIs REST
@RequestMapping("/api/v1/mortalidad")                                            // Establece la ruta base URL unificada en su versión 1 para este módulo
public class MortalidadController {

    private final MortalidadService service;                                     // Variable que almacena la capa de servicio para interactuar con la BD

    public MortalidadController(MortalidadService service) {                     // Constructor que inyecta automáticamente la dependencia del servicio
        this.service = service;                                                  // Asocia el servicio inyectado a la variable local de control
    }

    @GetMapping                                                                  // Atiende las peticiones HTTP GET en la raíz (/api/v1/mortalidad)
    public ResponseEntity<List<Mortalidad>> listar() {                           // Método que devuelve la lista completa de mortalidades
        return ResponseEntity.ok(service.getAll());                              // Consulta la base de datos y responde con un código HTTP 200 OK
    }

    @PostMapping                                                                 // Atiende las peticiones HTTP POST para añadir nuevos registros
    public ResponseEntity<?> guardar(@Valid @RequestBody CreateMortalidadRequest request) { // Recibe el JSON del cuerpo, lo valida y lo mapea al DTO

        // El Mapper transforma los datos de transferencia (DTO) en la entidad de BD
        Mortalidad m = MortalidadMapper.toModel(request);                        // Convierte el Request validado en el modelo físico "Mortalidad"
        Mortalidad nueva = service.save(m);                                      // Guarda en BD (Y aquí adentro se evalúa si gatilla el 0.15% hacia Alertas)
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);            // Retorna el registro creado acompañado de un HTTP 201 Created
    }

    // ==========================================
    // MÉTODO DE NEGOCIO CUSTOM
    // ==========================================

    @GetMapping("/jaula/{id}/promedio")                                          // Atiende peticiones GET en rutas dinámicas (ej: /jaula/3/promedio)
    public ResponseEntity<Double> promedio(@PathVariable int id) {               // Extrae el ID de la jaula directamente desde la URL
        double promedio = service.calcularPromedio(id);                          // Llama a la lógica del servicio para procesar matemáticamente el promedio
        return ResponseEntity.ok(promedio);                                      // Devuelve el resultado numérico (Double) con un HTTP 200 OK
    }
}