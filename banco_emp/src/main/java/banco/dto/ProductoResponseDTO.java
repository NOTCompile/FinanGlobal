package banco.dto;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) utilizado para enviar información
 * del Producto al cliente, incluyendo detalles de las relaciones
 * TipoProducto y Usuario para evitar el error de Lazy Loading.
 */
public class ProductoResponseDTO {

    private Integer id;
    private String nombre;
    private BigDecimal valorSolicitado;

    // Información del TipoProducto
    private Integer idTipoProducto;
    private String nombreTipoProducto;

    // Información del Usuario
    private Integer idUsuario;
    private String nombreUsuario;

    // --- Getters y Setters ---

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

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getNombreTipoProducto() {
        return nombreTipoProducto;
    }

    public void setNombreTipoProducto(String nombreTipoProducto) {
        this.nombreTipoProducto = nombreTipoProducto;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}