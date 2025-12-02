package banco.controllers;

import banco.models.Tarjeta;
import banco.services.TarjetaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tarjetas")
public class TarjetaController {

    private final TarjetaService tarjetaService;

    public TarjetaController(TarjetaService tarjetaService) {
        this.tarjetaService = tarjetaService;
    }

    @GetMapping
    public ResponseEntity<List<Tarjeta>> getAll() {
        List<Tarjeta> tarjetas = tarjetaService.findAll();
        return ResponseEntity.ok(tarjetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarjeta> getById(@PathVariable Integer id) {
        return tarjetaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cuenta/{idCuenta}")
    public ResponseEntity<List<Tarjeta>> getByCuentaId(@PathVariable Integer idCuenta) {
        List<Tarjeta> tarjetas = tarjetaService.findByCuentaId(idCuenta);
        return ResponseEntity.ok(tarjetas);
    }

    @PostMapping
    public ResponseEntity<Tarjeta> create(@RequestBody Tarjeta tarjeta) {
        Tarjeta savedTarjeta = tarjetaService.save(tarjeta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTarjeta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarjeta> update(@PathVariable Integer id, @RequestBody Tarjeta tarjeta) {
        tarjeta.setId(id);
        Tarjeta updatedTarjeta = tarjetaService.update(tarjeta);
        return ResponseEntity.ok(updatedTarjeta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tarjetaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

