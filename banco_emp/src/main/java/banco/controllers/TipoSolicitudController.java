package banco.controllers;

import banco.models.TipoSolicitud;
import banco.services.TipoSolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipo-solicitudes")
public class TipoSolicitudController {

    private final TipoSolicitudService tipoSolicitudService;

    public TipoSolicitudController(TipoSolicitudService tipoSolicitudService) {
        this.tipoSolicitudService = tipoSolicitudService;
    }

    @GetMapping
    public ResponseEntity<List<TipoSolicitud>> getAll() {
        List<TipoSolicitud> tipoSolicitudes = tipoSolicitudService.findAll();
        return ResponseEntity.ok(tipoSolicitudes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSolicitud> getById(@PathVariable Integer id) {
        return tipoSolicitudService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoSolicitud> create(@RequestBody TipoSolicitud tipoSolicitud) {
        TipoSolicitud savedTipoSolicitud = tipoSolicitudService.save(tipoSolicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTipoSolicitud);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSolicitud> update(@PathVariable Integer id, @RequestBody TipoSolicitud tipoSolicitud) {
        tipoSolicitud.setId(id);
        TipoSolicitud updatedTipoSolicitud = tipoSolicitudService.update(tipoSolicitud);
        return ResponseEntity.ok(updatedTipoSolicitud);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tipoSolicitudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

