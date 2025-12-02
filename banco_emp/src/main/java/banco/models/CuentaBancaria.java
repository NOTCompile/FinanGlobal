package banco.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta_bancaria")
public class CuentaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "n_cuenta")
    private String nCuenta;

    @Column(name = "n_intercuenta")
    private String n_intercuenta;

    private String nombre;

    private BigDecimal saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"contrasena", "nombre_usuario", "rol_usuario"})
    private Usuario usuario;

    // ===== Getters y Setters =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNCuenta() {
        return nCuenta;
    }

    public void setNCuenta(String nCuenta) {
        this.nCuenta = nCuenta;
    }

    public String getN_intercuenta() {
        return n_intercuenta;
    }

    public void setN_intercuenta(String n_intercuenta) {
        this.n_intercuenta = n_intercuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}