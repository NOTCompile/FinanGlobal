package banco.controllers;

import banco.models.TipoDocumento;
import banco.services.TipoDocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-documento")
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;

    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    // 1. OBTENER TODOS
    @GetMapping
    public List<TipoDocumento> getAllTiposDocumento() {
        return tipoDocumentoService.findAll();
    }

    // 2. OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumento> getTipoDocumentoById(@PathVariable Integer id) {
        return tipoDocumentoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR NUEVO
    @PostMapping
    public ResponseEntity<TipoDocumento> createTipoDocumento(@RequestBody TipoDocumento tipoDocumento) {
        TipoDocumento guardado = tipoDocumentoService.save(tipoDocumento);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 4. ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumento> updateTipoDocumento(@PathVariable Integer id, @RequestBody TipoDocumento tipoDocumento) {
        if (!id.equals(tipoDocumento.getId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            TipoDocumento actualizado = tipoDocumentoService.update(tipoDocumento);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoDocumento(@PathVariable Integer id) {
        tipoDocumentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}