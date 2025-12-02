package banco.controllers;

import banco.models.TipoProducto;
import banco.services.TipoProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-producto") // URL de base
public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    public TipoProductoController(TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    // 1. OBTENER TODOS GET http://localhost:8080/api/api/tipos-producto
    @GetMapping
    public List<TipoProducto> getAllTiposProducto() {
        return tipoProductoService.findAll();
    }

    // 2. OBTENER POR ID GET http://localhost:8080/api/api/tipos-producto/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TipoProducto> getTipoProductoById(@PathVariable Integer id) {
        return tipoProductoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR NUEVO POST http://localhost:8080/api/api/tipos-producto
    @PostMapping
    public ResponseEntity<TipoProducto> createTipoProducto(@RequestBody TipoProducto tipoProducto) {
        TipoProducto guardado = tipoProductoService.save(tipoProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 4. ACTUALIZAR PUT http://localhost:8080/api/api/tipos-producto/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TipoProducto> updateTipoProducto(@PathVariable Integer id, @RequestBody TipoProducto tipoProducto) {
        if (!id.equals(tipoProducto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            TipoProducto actualizado = tipoProductoService.update(tipoProducto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. ELIMINAR DELETE http://localhost:8080/api/api/tipos-producto/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoProducto(@PathVariable Integer id) {
        tipoProductoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}