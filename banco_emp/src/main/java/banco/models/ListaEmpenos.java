package banco.models;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_empenos")
public class ListaEmpenos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_empeno")
    private Empeno empeno;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Empeno getEmpeno() {
        return empeno;
    }

    public void setEmpeno(Empeno empeno) {
        this.empeno = empeno;
    }
}