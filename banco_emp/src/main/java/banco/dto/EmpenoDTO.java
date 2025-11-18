package banco.dto; // Crear este paquete si no existe

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmpenoDTO {

    private BigDecimal valorPrestado;
    private BigDecimal valorRecuperacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private Integer idProducto; // Solo el ID
    private Integer idEstado;   // Solo el ID

    // Constructor vacío
    public EmpenoDTO() {
    }

    // Getters y Setters

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

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
}