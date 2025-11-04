package banco.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import banco.models.Usuario;
import banco.services.UsuariosService; // Solo se importa el Service

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para manejar operaciones de Usuario.
 * Habla exclusivamente con la capa de servicio.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    // 1. Inyección: Solo se inyecta el Service
        private final UsuariosService usuariosService;

    // Inyección por Constructor (limpio y recomendado)
    public UsuarioController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    /**
     * GET /api/usuarios - Obtener todos los usuarios
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        // Llama al servicio, que maneja las transacciones y el acceso al DAO
        List<Usuario> usuarios = usuariosService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * GET /api/usuarios/{id} - Obtener un usuario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        return usuariosService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/usuarios - Crear un nuevo usuario
     */
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        try {
            // El servicio maneja la validación de duplicados (DNI/correo)
            Usuario savedUsuario = usuariosService.save(usuario);
            return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Excepción lanzada por el servicio al encontrar un duplicado
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Captura errores generales
            return ResponseEntity.internalServerError().body("Error al crear usuario.");
        }
    }

    /**
     * PUT /api/usuarios/{id} - Actualizar un usuario existente
     */
    // Modificar aquí: de <Usuario> a <?> o <Object>
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {

        if (usuario.getId_usuario() == null || !usuario.getId_usuario().equals(id)) {
            // Devuelve ResponseEntity<String> (400 Bad Request)
            return ResponseEntity.badRequest().body("El ID de la ruta no coincide con el ID del cuerpo.");
        }

        // Si llegamos aquí, queremos devolver ResponseEntity<Usuario> (200 OK)
        if (usuariosService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario updatedUsuario = usuariosService.update(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }

    /**
     * DELETE /api/usuarios/{id} - Eliminar un usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuariosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- MÉTODOS DE BÚSQUEDA Y LOGIN ---

    /**
     * GET /api/usuarios/dni/{dni} - Buscar usuario por DNI
     */
    @GetMapping("/dni/{dni}")
    public ResponseEntity<Usuario> getUsuarioByDni(@PathVariable String dni) {
        return usuariosService.findByDniRuc(dni)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * GET /api/usuarios/email/{email} - Buscar usuario por email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email) {
        return usuariosService.findByCorreo(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * GET /api/usuarios/tipo/{tipo} - Obtener usuarios por tipo
     */
    @GetMapping("/tipo/{tipo}")
   /* public ResponseEntity<List<Usuario>> getUsuariosByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(usuariosService.findByRol(tipo.toUpperCase()));

    }*/
    public ResponseEntity<List<Usuario>> getUsuariosByTipo(@PathVariable Integer tipo) {
        return ResponseEntity.ok(usuariosService.findByRol(tipo));
    }

    /**
     * POST /api/usuarios/login - Iniciar sesión
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        // Delega toda la lógica de validación de correo/contraseña al servicio
        Optional<Usuario> usuarioOpt = usuariosService.login(
                loginRequest.getCorreo(),
                loginRequest.getContrasena()
        );

        if (usuarioOpt.isEmpty()) {
            // Devuelve 401 si el login falla (correo no existe o contraseña incorrecta)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos.");
        }

        return ResponseEntity.ok(usuarioOpt.get());
    }
}