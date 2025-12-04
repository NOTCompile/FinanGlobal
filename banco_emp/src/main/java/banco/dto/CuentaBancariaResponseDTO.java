package banco.dto;

import java.math.BigDecimal;

/**
 * DTO utilizado para enviar datos de la cuenta bancaria al frontend,
 * incluyendo el nombre del usuario asociado para facilitar la visualización
 * en tablas (ej. la lista de todas las cuentas).
 */
public class CuentaBancariaResponseDTO {

    private Integer id;
    private String nombre;
    private String nCuenta;
    private String nIntercuenta;
    private BigDecimal saldo;
    private Integer idUsuario;

    // ⭐ Campo adicional solicitado: Nombre del titular para visualización ⭐
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

    public String getNCuenta() {
        return nCuenta;
    }

    public void setNCuenta(String nCuenta) {
        this.nCuenta = nCuenta;
    }

    public String getNIntercuenta() {
        return nIntercuenta;
    }

    public void setNIntercuenta(String nIntercuenta) {
        this.nIntercuenta = nIntercuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
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