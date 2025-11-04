package banco.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta_bancaria")
public class CuentaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "n_cuenta") // Mapea a la columna SQL "n_cuenta"
    private String numero_cuenta;

    @Column(name = "n_intercuenta") // Mapea a la columna SQL "n_intercuenta"
    private String n_intercuenta;

    // Mapea a la columna SQL "nombre"
    private String nombre;

    // ELIMINADAS: private String banco;
    // ELIMINADAS: private String tipo_cuenta;

    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Mapeo para n_cuenta
    public String getNumero_cuenta() {
        return numero_cuenta;
    }

    public void setNumero_cuenta(String numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    // Mapeo para n_intercuenta
    public String getN_intercuenta() {
        return n_intercuenta;
    }

    public void setN_intercuenta(String n_intercuenta) {
        this.n_intercuenta = n_intercuenta;
    }

    // Mapeo para nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // El getter y setter para 'banco' y 'tipo_cuenta' DEBEN ser eliminados.

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