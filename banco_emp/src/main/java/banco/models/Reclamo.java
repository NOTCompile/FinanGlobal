package banco.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reclamo")
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // PK serial

    // --- Relaciones de Clave Foránea (FK) ---

    // FK a t_usuario (id_usuario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // Asume que ya existe la clase Usuario (t_usuario)

    // FK a tipo_reclamo (id_tipo_reclamo) - ESTO ES LO QUE SOLICITASTE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_reclamo", nullable = false)
    private TipoReclamo tipoReclamo;

    // FK a estado (id_estado)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado") // Nullable en la BD
    private Estado estado; // Asume que existe una clase Estado

    // --- Columnas de Datos ---

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "fecha_reclamo", nullable = false)
    private LocalDate fechaReclamo; // Mapeado a DATE

    @Column(name = "fecha_solucion")
    private LocalDate fechaSolucion; // Mapeado a DATE

    @Column(name = "detalle_solucion", columnDefinition = "TEXT")
    private String detalleSolucion;

    // Constructor por defecto
    public Reclamo() {
    }

    // ===== Getters y Setters =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getters y Setters para relaciones
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoReclamo getTipoReclamo() {
        return tipoReclamo;
    }

    public void setTipoReclamo(TipoReclamo tipoReclamo) {
        this.tipoReclamo = tipoReclamo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    // Getters y Setters para columnas de datos
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaReclamo() {
        return fechaReclamo;
    }

    public void setFechaReclamo(LocalDate fechaReclamo) {
        this.fechaReclamo = fechaReclamo;
    }

    public LocalDate getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(LocalDate fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public String getDetalleSolucion() {
        return detalleSolucion;
    }

    public void setDetalleSolucion(String detalleSolucion) {
        this.detalleSolucion = detalleSolucion;
    }
}