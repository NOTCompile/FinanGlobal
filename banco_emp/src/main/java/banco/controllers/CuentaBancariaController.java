package banco.controllers;

import banco.models.CuentaBancaria;
import banco.models.Usuario;
import banco.services.CuentaBancariaService; // Asumiendo que tienes un servicio
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import banco.services.UsuariosService;
import banco.dto.CuentaBancariaDTO;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/cuentas") // <-- URL de base (ej: http://localhost:8080/api/cuentas)
public class CuentaBancariaController {

    private final CuentaBancariaService cuentaBancariaService;
    private final UsuariosService usuariosService; // 1. Nueva dependencia inyectada

    // Inyección por constructor (la mejor práctica)
    public CuentaBancariaController(CuentaBancariaService cuentaBancariaService, UsuariosService usuariosService) {
        this.cuentaBancariaService = cuentaBancariaService;
        this.usuariosService = usuariosService;
    }

    // 1. OBTENER TODAS LAS CUENTAS (GET /api/cuentas)
    @GetMapping
    public List<CuentaBancaria> getAllCuentas() {
        return cuentaBancariaService.findAll();
    }

    // 2. OBTENER POR ID (GET /api/cuentas/{id})
    @GetMapping("/{id}")
    public ResponseEntity<CuentaBancaria> getCuentaById(@PathVariable Integer id) {
        return cuentaBancariaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR NUEVA CUENTA (POST /api/cuentas)
    @PostMapping
    public ResponseEntity<CuentaBancaria> createCuenta(@RequestBody CuentaBancariaDTO cuentaDto) {

        // 1. Usa el idUsuario del DTO para buscar el objeto Usuario completo en la DB
        Usuario usuario = usuariosService.findById(cuentaDto.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario con ID " + cuentaDto.getIdUsuario() + " no encontrado para asignar la cuenta."
                ));

        // 2. Mapeamos los datos del DTO a la entidad CuentaBancaria
        CuentaBancaria nuevaCuenta = new CuentaBancaria();
        nuevaCuenta.setNombre(cuentaDto.getNombre());
        nuevaCuenta.setNCuenta(cuentaDto.getNumero_cuenta());
        nuevaCuenta.setN_intercuenta(cuentaDto.getN_intercuenta());
        nuevaCuenta.setSaldo(cuentaDto.getSaldo());

        // 3. Asignamos la relación: la clave para que JPA/JDBC funcione
        nuevaCuenta.setUsuario(usuario);

        // 4. Guardamos la entidad completa
        CuentaBancaria guardada = cuentaBancariaService.save(nuevaCuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }


    // 4. ACTUALIZAR CUENTA (PUT /api/cuentas/{id})
    @PutMapping("/{id}")
    public ResponseEntity<CuentaBancaria> updateCuenta(@PathVariable Integer id, @RequestBody CuentaBancaria cuenta) {
        // Lógica de actualización, asegurando que el ID sea correcto
        if (!id.equals(cuenta.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cuentaBancariaService.update(cuenta));
    }

    // 5. ELIMINAR CUENTA (DELETE /api/cuentas/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Integer id) {
        cuentaBancariaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}