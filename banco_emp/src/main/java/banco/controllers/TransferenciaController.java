package banco.controllers;

import banco.models.Transferencia;
import banco.dto.TransferenciaDTO;
import banco.services.TransferenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    // 1. REGISTRAR / REALIZAR TRANSFERENCIA (RF002)
    @PostMapping("/realizar")
    public ResponseEntity<Transferencia> realizarTransferencia(@RequestBody TransferenciaDTO dto) {
        try {
            Transferencia transferencia = transferenciaService.realizarTransferencia(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(transferencia);
        } catch (ResponseStatusException e) {
            // Manejo de excepciones (ej: Saldo insuficiente, Cuenta no encontrada)
            throw e;
        } catch (Exception e) {
            // Error interno
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2. CONSULTAR HISTORIAL (GET /api/transferencias)
    @GetMapping
    public List<Transferencia> getAllTransferencias() {
        return transferenciaService.findAll();
    }

    // 3. OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Transferencia> getTransferenciaById(@PathVariable Integer id) {
        return transferenciaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}