package banco.controllers;

import banco.models.Reclamo;
import banco.services.ReclamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reclamos")
public class ReclamoController {

    private final ReclamoService reclamoService;

    public ReclamoController(ReclamoService reclamoService) {
        this.reclamoService = reclamoService;
    }

    @GetMapping
    public ResponseEntity<List<Reclamo>> getAll() {
        List<Reclamo> reclamos = reclamoService.findAll();
        return ResponseEntity.ok(reclamos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamo> getById(@PathVariable Integer id) {
        return reclamoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Reclamo>> getByUsuarioId(@PathVariable Integer idUsuario) {
        List<Reclamo> reclamos = reclamoService.findByUsuarioId(idUsuario);
        return ResponseEntity.ok(reclamos);
    }

    @PostMapping
    public ResponseEntity<Reclamo> create(@RequestBody Reclamo reclamo) {
        Reclamo savedReclamo = reclamoService.save(reclamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReclamo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reclamo> update(@PathVariable Integer id, @RequestBody Reclamo reclamo) {
        reclamo.setId(id);
        Reclamo updatedReclamo = reclamoService.update(reclamo);
        return ResponseEntity.ok(updatedReclamo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        reclamoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

