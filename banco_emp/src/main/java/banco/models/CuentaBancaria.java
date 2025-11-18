package banco.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cuenta_bancaria")
public class CuentaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // CAMBIO CLAVE 1:
    // El BeanPropertyRowMapper mapea 'n_cuenta' a 'nCuenta'.
    @Column(name = "n_cuenta")
    private String nCuenta; // Nombre de propiedad ajustado (camelCase de n_cuenta)

    @Column(name = "n_intercuenta")
    private String n_intercuenta;

    private String nombre;

    private BigDecimal saldo;

    // --- Mapeo para JdbcTemplate (id simple) ---
    // CAMBIO CLAVE 2:
    // Agregamos un campo para capturar el ID del usuario directamente de la consulta
    // SQL (SELECT id_usuario...). El RowMapper asignará el valor aquí.
    // Indica a JPA que ignore este campo
    //@Transient
    //private Integer idUsuario;

    // --- Mapeo para JPA (objeto completo) ---
    // Mantenemos la relación JPA para cuando uses Spring Data JPA.
    /*@ManyToOne(fetch = FetchType.LAZY) // Se puede usar LAZY, ya que Jackson solo necesita el ID.
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIdentityReference(alwaysAsId = true) // 🔹 Muestra solo el ID del usuario
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_usuario")
    private Usuario usuario;


    // ===== Getters y Setters =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Mapeo corregido para n_cuenta
    public String getNCuenta() { // Se espera getNCuenta()
        return nCuenta;
    }

    public void setNCuenta(String nCuenta) { // Se espera setNCuenta()
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

    // Getter y Setter para el ID simple, necesario si usas JdbcTemplate
   /* public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }*/
}