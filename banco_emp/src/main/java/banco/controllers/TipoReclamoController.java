package banco.controllers;

import banco.models.TipoReclamo;
import banco.services.TipoReclamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipo-reclamos")
public class TipoReclamoController {

    private final TipoReclamoService tipoReclamoService;

    public TipoReclamoController(TipoReclamoService tipoReclamoService) {
        this.tipoReclamoService = tipoReclamoService;
    }

    @GetMapping
    public ResponseEntity<List<TipoReclamo>> getAll() {
        List<TipoReclamo> tipoReclamos = tipoReclamoService.findAll();
        return ResponseEntity.ok(tipoReclamos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoReclamo> getById(@PathVariable Integer id) {
        return tipoReclamoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoReclamo> create(@RequestBody TipoReclamo tipoReclamo) {
        TipoReclamo savedTipoReclamo = tipoReclamoService.save(tipoReclamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTipoReclamo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoReclamo> update(@PathVariable Integer id, @RequestBody TipoReclamo tipoReclamo) {
        tipoReclamo.setId(id);
        TipoReclamo updatedTipoReclamo = tipoReclamoService.update(tipoReclamo);
        return ResponseEntity.ok(updatedTipoReclamo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tipoReclamoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

