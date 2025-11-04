package banco.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "empeno")
public class Empeno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor_prestado", precision = 9, scale = 2)
    private BigDecimal valorPrestado;

    @Column(name = "valor_recuperacion", precision = 9, scale = 2)
    private BigDecimal valorRecuperacion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_final")
    private LocalDate fechaFinal;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estado estado;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValorPrestado() {
        return valorPrestado;
    }

    public void setValorPrestado(BigDecimal valorPrestado) {
        this.valorPrestado = valorPrestado;
    }

    public BigDecimal getValorRecuperacion() {
        return valorRecuperacion;
    }

    public void setValorRecuperacion(BigDecimal valorRecuperacion) {
        this.valorRecuperacion = valorRecuperacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}