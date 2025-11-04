package banco.models;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_solicitudes")
public class ListaSolicitudes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuarioSolicitante; // Usuario que hace la solicitud

    @ManyToOne
    @JoinColumn(name = "id_solicitud")
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "id_usuario_banco")
    private Usuario usuarioBanco; // Usuario del banco que gestiona

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