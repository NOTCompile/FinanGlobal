package banco.controllers;

import banco.models.TipoTarjeta;
import banco.services.TipoTarjetaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipo-tarjetas")
public class TipoTarjetaController {

    private final TipoTarjetaService tipoTarjetaService;

    public TipoTarjetaController(TipoTarjetaService tipoTarjetaService) {
        this.tipoTarjetaService = tipoTarjetaService;
    }

    @GetMapping
    public ResponseEntity<List<TipoTarjeta>> getAll() {
        List<TipoTarjeta> tipoTarjetas = tipoTarjetaService.findAll();
        return ResponseEntity.ok(tipoTarjetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTarjeta> getById(@PathVariable Integer id) {
        return tipoTarjetaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoTarjeta> create(@RequestBody TipoTarjeta tipoTarjeta) {
        TipoTarjeta savedTipoTarjeta = tipoTarjetaService.save(tipoTarjeta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTipoTarjeta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTarjeta> update(@PathVariable Integer id, @RequestBody TipoTarjeta tipoTarjeta) {
        tipoTarjeta.setId(id);
        TipoTarjeta updatedTipoTarjeta = tipoTarjetaService.update(tipoTarjeta);
        return ResponseEntity.ok(updatedTipoTarjeta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tipoTarjetaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

