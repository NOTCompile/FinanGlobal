package banco.dto;


import java.io.Serializable;

/**
 * DTO para la entidad Usuario.
 * Evita exponer datos sensibles (como contraseñas) en las respuestas del API.
 */
public class UsuarioDTO implements Serializable {

    private Integer id;
    private String nombre;
    private String apellidos;
    private String dni_ruc;
    private String correo;
    private String contrasena;
    private String telefono;
    private String direccion;
    private String nombre_usuario;
    private Integer rol_usuario;

    // Constructor vacío (requerido por frameworks)
    public UsuarioDTO(Integer idUsuario, String nombre, String apellidos, String dniRuc, String correo, String contrasena,String telefono, String direccion, String nombreUsuario, String rolUsuario) {
    }

    // Constructor completo
    public UsuarioDTO(Integer id, String nombre, String apellidos, String dni_ruc, String correo,String contrasena,
                      String telefono, String direccion, String nombre_usuario, Integer rol_usuario) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni_ruc = dni_ruc;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nombre_usuario = nombre_usuario;
        this.rol_usuario = rol_usuario;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni_ruc() {
        return dni_ruc;
    }

    public void setDni_ruc(String dni_ruc) {
        this.dni_ruc = dni_ruc;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public Integer getRol_usuario() {
        return rol_usuario;
    }

    public void setRol_usuario(Integer rol_usuario) {
        this.rol_usuario = rol_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
