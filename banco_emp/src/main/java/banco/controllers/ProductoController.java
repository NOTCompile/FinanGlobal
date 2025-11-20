package banco.controllers;

import banco.models.Producto;
import banco.models.TipoProducto;
import banco.services.ProductoService;
import banco.services.TipoProductoService; // Necesario para buscar la relación
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/productos") // URL de base (ej: http://localhost:8080/api/productos)
public class ProductoController {

    private final ProductoService productoService;
    private final TipoProductoService tipoProductoService; // Inyectamos la dependencia de TipoProducto

    // Inyección por constructor
    public ProductoController(ProductoService productoService, TipoProductoService tipoProductoService) {
        this.productoService = productoService;
        this.tipoProductoService = tipoProductoService;
    }

    // 1. OBTENER TODOS LOS PRODUCTOS (GET /api/productos)
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    // 2. OBTENER POR ID (GET /api/productos/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR NUEVO PRODUCTO (POST /api/productos)

    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {

        // El cliente solo envía el ID en el objeto TipoProducto (producto.getTipoProducto().getId())
        Integer idTipo = producto.getTipoProducto().getId();

        // 1. Buscamos el objeto TipoProducto completo para asignarlo
        TipoProducto tipoProducto = tipoProductoService.findById(idTipo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de Producto con ID " + idTipo + " no encontrado para asignar al producto."
                ));

        // 2. Asignamos el objeto completo al producto
        producto.setTipoProducto(tipoProducto);

        // 3. Guardamos la entidad
        Producto guardado = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 4. ACTUALIZAR PRODUCTO (PUT /api/productos/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        if (!id.equals(producto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        // Si la actualización incluye la relación, buscamos y asignamos el TipoProducto
        if (producto.getTipoProducto() != null && producto.getTipoProducto().getId() != null) {
            Integer idTipo = producto.getTipoProducto().getId();
            TipoProducto tipoProducto = tipoProductoService.findById(idTipo)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Tipo de Producto con ID " + idTipo + " no encontrado durante la actualización."
                    ));
            producto.setTipoProducto(tipoProducto);
        }

        try {
            Producto actualizado = productoService.update(producto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. ELIMINAR PRODUCTO (DELETE /api/productos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    // NUEVA RUTA: GET /api/productos/usuario/{idUsuario}
    @GetMapping("/usuario/{idUsuario}")
    public List<Producto> getProductosByUsuarioId(@PathVariable Integer idUsuario) {
        // Lógica: llama al servicio para obtener la lista
        return productoService.findByUsuarioId(idUsuario);
    }
}