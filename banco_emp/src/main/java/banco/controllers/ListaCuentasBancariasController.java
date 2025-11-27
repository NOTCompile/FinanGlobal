package banco.controllers;

import banco.models.ListaCuentasBancarias;
import banco.models.CuentaBancaria; // Necesario para la relación
import banco.models.Usuario; // Necesario para la relación
import banco.services.ListaCuentasBancariasService;
import banco.services.CuentaBancariaService; // Asumido
import banco.services.UsuariosService; // Asumido
import banco.dto.ListaCuentasBancariasDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/listacuentasbancarias")
public class ListaCuentasBancariasController {

    private final ListaCuentasBancariasService listaCuentasBancariasService;
    private final CuentaBancariaService cuentaBancariaService; // Servicio de CuentaBancaria
    private final UsuariosService usuarioService; // Servicio de Usuario

    // Inyección por constructor
    public ListaCuentasBancariasController(
            ListaCuentasBancariasService listaCuentasBancariasService,
            CuentaBancariaService cuentaBancariaService,
            UsuariosService usuarioService) {
        this.listaCuentasBancariasService = listaCuentasBancariasService;
        this.cuentaBancariaService = cuentaBancariaService;
        this.usuarioService = usuarioService;
    }

    // 1. OBTENER TODOS
    @GetMapping
    public List<ListaCuentasBancarias> getAll() {
        return listaCuentasBancariasService.findAll();
    }

    // 2. OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ListaCuentasBancarias> getById(@PathVariable Integer id) {
        return listaCuentasBancariasService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR NUEVO REGISTRO (POST /api/listacuentasbancarias)
    @PostMapping
    public ResponseEntity<ListaCuentasBancarias> create(@RequestBody ListaCuentasBancariasDTO listaCuentasBancariasDto) {

        // 1. Buscamos el objeto CuentaBancaria
        CuentaBancaria cuentaBancaria = cuentaBancariaService.findById(listaCuentasBancariasDto.getIdCuenta())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cuenta Bancaria con ID " + listaCuentasBancariasDto.getIdCuenta() + " no encontrada."
                ));

        // 2. Buscamos el objeto Usuario
        Usuario usuario = usuarioService.findById(listaCuentasBancariasDto.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario con ID " + listaCuentasBancariasDto.getIdUsuario() + " no encontrado."
                ));

        // 3. Mapeamos DTO a Entidad
        ListaCuentasBancarias nuevoRegistro = new ListaCuentasBancarias();

        // 4. Asignamos las relaciones
        nuevoRegistro.setCuentaBancaria(cuentaBancaria);
        nuevoRegistro.setUsuario(usuario);

        // 5. Guardamos
        ListaCuentasBancarias guardado = listaCuentasBancariasService.save(nuevoRegistro);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 4. ACTUALIZAR REGISTRO (PUT /api/listacuentasbancarias/{id})
    @PutMapping("/{id}")
    public ResponseEntity<ListaCuentasBancarias> update(@PathVariable Integer id, @RequestBody ListaCuentasBancarias listaCuentasBancarias) {
        if (!id.equals(listaCuentasBancarias.getId())) {
            return ResponseEntity.badRequest().build();
        }

        // Si se intenta actualizar la relación de CuentaBancaria, buscamos la entidad completa
        if (listaCuentasBancarias.getCuentaBancaria() != null && listaCuentasBancarias.getCuentaBancaria().getId() != null) {
            Integer idCuenta = listaCuentasBancarias.getCuentaBancaria().getId();
            CuentaBancaria cuentaBancaria = cuentaBancariaService.findById(idCuenta)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Cuenta Bancaria con ID " + idCuenta + " no encontrada durante la actualización."
                    ));
            listaCuentasBancarias.setCuentaBancaria(cuentaBancaria);
        }

        // Si se intenta actualizar la relación de Usuario, buscamos la entidad completa
        if (listaCuentasBancarias.getUsuario() != null && listaCuentasBancarias.getUsuario().getId_usuario() != null) { // Asumiendo getIdUsuario()
            Integer idUsuario = listaCuentasBancarias.getUsuario().getId_usuario();
            Usuario usuario = usuarioService.findById(idUsuario)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Usuario con ID " + idUsuario + " no encontrado durante la actualización."
                    ));
            listaCuentasBancarias.setUsuario(usuario);
        }

        try {
            ListaCuentasBancarias actualizado = listaCuentasBancariasService.update(listaCuentasBancarias);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. ELIMINAR REGISTRO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        listaCuentasBancariasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}