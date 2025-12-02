package banco.controllers;

import banco.models.TipoTransferencia;
import banco.services.TipoTransferenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-transferencia")
public class TipoTransferenciaController {

    private final TipoTransferenciaService tipoTransferenciaService;

    public TipoTransferenciaController(TipoTransferenciaService tipoTransferenciaService) {
        this.tipoTransferenciaService = tipoTransferenciaService;
    }

    // 1. OBTENER TODOS
    @GetMapping
    public List<TipoTransferencia> getAllTiposTransferencia() {
        return tipoTransferenciaService.findAll();
    }

    // 2. OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoTransferencia> getTipoTransferenciaById(@PathVariable Integer id) {
        return tipoTransferenciaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR NUEVO
    @PostMapping
    public ResponseEntity<TipoTransferencia> createTipoTransferencia(@RequestBody TipoTransferencia tipoTransferencia) {
        TipoTransferencia guardado = tipoTransferenciaService.save(tipoTransferencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 4. ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<TipoTransferencia> updateTipoTransferencia(@PathVariable Integer id, @RequestBody TipoTransferencia tipoTransferencia) {
        if (!id.equals(tipoTransferencia.getId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            TipoTransferencia actualizado = tipoTransferenciaService.update(tipoTransferencia);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoTransferencia(@PathVariable Integer id) {
        tipoTransferenciaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}