package banco.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

// Este DTO se usa para recibir datos de entrada (POST/PUT)
// Captura el ID del usuario en lugar del objeto completo de Usuario
public class CuentaBancariaDTO {

    private String nombre;
    private String numero_cuenta;
    private String n_intercuenta;
    private BigDecimal saldo;

    // CLAVE: Usamos el ID como Integer, no el objeto Usuario completo
    @JsonProperty("id_usuario") // <--- ¡Asegura que Jackson mapee 'id_usuario' del JSON!
    private Integer idUsuario;

    // Constructor vacío
    public CuentaBancariaDTO() {}

    // Getters y Setters (Necesarios para que Jackson deserialice el JSON)

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero_cuenta() {
        return numero_cuenta;
    }

    public void setNumero_cuenta(String numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    public String getN_intercuenta() {
        return n_intercuenta;
    }

    public void setN_intercuenta(String n_intercuenta) {
        this.n_intercuenta = n_intercuenta;
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
}
