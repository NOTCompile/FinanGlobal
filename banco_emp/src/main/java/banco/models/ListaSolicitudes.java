package banco.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "lista_solicitudes")
public class ListaSolicitudes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({"contrasena", "nombre_usuario", "rol_usuario"})
    private Usuario usuarioSolicitante;

    @ManyToOne
    @JoinColumn(name = "id_solicitud")
    @JsonIgnoreProperties({"producto"})
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "id_usuario_banco")
    @JsonIgnoreProperties({"contrasena", "nombre_usuario", "rol_usuario"})
    private Usuario usuarioBanco;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public void setUsuarioSolicitante(Usuario usuarioSolicitante) {
        this.usuarioSolicitante = usuarioSolicitante;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Usuario getUsuarioBanco() {
        return usuarioBanco;
    }

    public void setUsuarioBanco(Usuario usuarioBanco) {
        this.usuarioBanco = usuarioBanco;
    }
}