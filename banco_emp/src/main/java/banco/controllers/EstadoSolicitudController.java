package banco.controllers;

import banco.models.EstadoSolicitud;
import banco.services.EstadoSolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estados-solicitud")
public class EstadoSolicitudController {

    private final EstadoSolicitudService estadoSolicitudService;

    public EstadoSolicitudController(EstadoSolicitudService estadoSolicitudService) {
        this.estadoSolicitudService = estadoSolicitudService;
    }

    // 1. OBTENER TODOS
    @GetMapping
    public List<EstadoSolicitud> getAllEstadosSolicitud() {
        return estadoSolicitudService.findAll();
    }

    // 2. OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<EstadoSolicitud> getEstadoSolicitudById(@PathVariable Integer id) {
        return estadoSolicitudService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR NUEVO
    @PostMapping
    public ResponseEntity<EstadoSolicitud> createEstadoSolicitud(@RequestBody EstadoSolicitud estadoSolicitud) {
        EstadoSolicitud guardado = estadoSolicitudService.save(estadoSolicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 4. ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<EstadoSolicitud> updateEstadoSolicitud(@PathVariable Integer id, @RequestBody EstadoSolicitud estadoSolicitud) {
        if (!id.equals(estadoSolicitud.getId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            EstadoSolicitud actualizado = estadoSolicitudService.update(estadoSolicitud);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstadoSolicitud(@PathVariable Integer id) {
        estadoSolicitudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}