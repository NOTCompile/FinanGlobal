package banco.controllers;

import banco.models.Empeno;
import banco.models.Producto; // Importar la clase Producto
import banco.models.Estado;   // Importar la clase Estado
import banco.services.EmpenoService;
import banco.services.ProductoService; // Nuevo
import banco.services.EstadoService;   // Nuevo
import banco.dto.EmpenoDTO;           // Nuevo
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/empenos")
public class EmpenoController {

    private final EmpenoService empenoService;
    private final ProductoService productoService; // 1. Nueva dependencia
    private final EstadoService estadoService;     // 2. Nueva dependencia

    // Inyección por constructor (con las nuevas dependencias)
    public EmpenoController(
            EmpenoService empenoService,
            ProductoService productoService,
            EstadoService estadoService) {
        this.empenoService = empenoService;
        this.productoService = productoService;
        this.estadoService = estadoService;
    }

    // ... (getAllEmpenos y getEmpenoById se mantienen igual) ...

    // 1. OBTENER TODOS (GET /api/empenos)
    @GetMapping
    public List<Empeno> getAllEmpenos() {
        return empenoService.findAll();
    }

    // 2. OBTENER POR ID (GET /api/empenos/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Empeno> getEmpenoById(@PathVariable Integer id) {
        return empenoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR NUEVO EMPENO (POST /api/empenos) - ¡USA EL DTO AHORA!
    @PostMapping
    public ResponseEntity<Empeno> createEmpeno(@RequestBody EmpenoDTO empenoDto) {

        // 1. Buscar y validar la entidad Producto
        Producto producto = productoService.findById(empenoDto.getIdProducto())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto con ID " + empenoDto.getIdProducto() + " no encontrado."
                ));

        // 2. Buscar y validar la entidad Estado
        Estado estado = estadoService.findById(empenoDto.getIdEstado())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estado con ID " + empenoDto.getIdEstado() + " no encontrado."
                ));

        // 3. Mapeamos los datos del DTO a la entidad Empeno
        Empeno nuevoEmpeno = new Empeno();
        nuevoEmpeno.setValorPrestado(empenoDto.getValorPrestado());
        nuevoEmpeno.setValorRecuperacion(empenoDto.getValorRecuperacion());
        nuevoEmpeno.setFechaInicio(empenoDto.getFechaInicio());
        nuevoEmpeno.setFechaFinal(empenoDto.getFechaFinal());

        // 4. Asignamos las relaciones (la clave para que JPA/JDBC funcione)
        nuevoEmpeno.setProducto(producto);
        nuevoEmpeno.setEstado(estado);

        // 5. Guardamos la entidad completa
        Empeno guardado = empenoService.save(nuevoEmpeno);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 4. ACTUALIZAR EMPENO (PUT /api/empenos/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Empeno> updateEmpeno(@PathVariable Integer id, @RequestBody Empeno empeno) {
        // Asegurarse de que el ID del path y el cuerpo coincidan
        if (!id.equals(empeno.getId())) {
            return ResponseEntity.badRequest().build();
        }

        // El servicio manejará la lógica de verificar si existe y actualizar
        try {
            Empeno actualizado = empenoService.update(empeno);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // O manejar la excepción de forma más específica
        }
    }

    // 5. ELIMINAR EMPENO (DELETE /api/empenos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpeno(@PathVariable Integer id) {
        empenoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}