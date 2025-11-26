package banco.models;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_cuentasbancarias")
public class ListaCuentasBancarias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) // Se recomienda Lazy por defecto
    @JoinColumn(name = "id_cuenta")
    private CuentaBancaria cuentaBancaria;

    @ManyToOne(fetch = FetchType.LAZY) // Se recomienda Lazy por defecto
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}