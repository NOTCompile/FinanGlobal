package banco.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transferencia")
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 9, scale = 2)
    private BigDecimal monto;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_usuario_emisor")
    @JsonIgnoreProperties({"contrasena", "nombre_usuario", "rol_usuario", "telefono", "direccion", "sexo"})
    private Usuario usuarioEmisor;

    @ManyToOne
    @JoinColumn(name = "id_cuenta_emisor")
    @JsonIgnoreProperties({"usuario"})
    private CuentaBancaria cuentaEmisora;

    @ManyToOne
    @JoinColumn(name = "id_usuario_receptor")
    @JsonIgnoreProperties({"contrasena", "nombre_usuario", "rol_usuario", "telefono", "direccion", "sexo"})
    private Usuario usuarioReceptor;

    @ManyToOne
    @JoinColumn(name = "id_cuenta_receptor")
    @JsonIgnoreProperties({"usuario"})
    private CuentaBancaria cuentaReceptora;

    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoTransferencia tipoTransferencia;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuarioEmisor() {
        return usuarioEmisor;
    }

    public void setUsuarioEmisor(Usuario usuarioEmisor) {
        this.usuarioEmisor = usuarioEmisor;
    }

    public CuentaBancaria getCuentaEmisora() {
        return cuentaEmisora;
    }

    public void setCuentaEmisora(CuentaBancaria cuentaEmisora) {
        this.cuentaEmisora = cuentaEmisora;
    }

    public Usuario getUsuarioReceptor() {
        return usuarioReceptor;
    }

    public void setUsuarioReceptor(Usuario usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
    }

    public CuentaBancaria getCuentaReceptora() {
        return cuentaReceptora;
    }

    public void setCuentaReceptora(CuentaBancaria cuentaReceptora) {
        this.cuentaReceptora = cuentaReceptora;
    }

    public TipoTransferencia getTipoTransferencia() {
        return tipoTransferencia;
    }

    public void setTipoTransferencia(TipoTransferencia tipoTransferencia) {
        this.tipoTransferencia = tipoTransferencia;
    }
}