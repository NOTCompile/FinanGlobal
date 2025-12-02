package banco.controllers;

import banco.models.Solicitud;
import banco.services.SolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public ResponseEntity<List<Solicitud>> getAll() {
        List<Solicitud> solicitudes = solicitudService.findAll();
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> getById(@PathVariable Integer id) {
        return solicitudService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<Solicitud>> getByProductoId(@PathVariable Integer idProducto) {
        List<Solicitud> solicitudes = solicitudService.findByProductoId(idProducto);
        return ResponseEntity.ok(solicitudes);
    }

    @PostMapping
    public ResponseEntity<Solicitud> create(@RequestBody Solicitud solicitud) {
        Solicitud savedSolicitud = solicitudService.save(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSolicitud);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> update(@PathVariable Integer id, @RequestBody Solicitud solicitud) {
        solicitud.setId(id);
        Solicitud updatedSolicitud = solicitudService.update(solicitud);
        return ResponseEntity.ok(updatedSolicitud);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        solicitudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

