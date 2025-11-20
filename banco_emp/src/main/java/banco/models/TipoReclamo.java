package banco.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_reclamo")
public class TipoReclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre; // Corresponde a 'nombre character varying(100)'

    @Column(length = 255)
    private String descripcion; // Corresponde a 'descripcion character varying(255)'

    // Constructor por defecto
    public TipoReclamo() {
    }

    // Getters y Setters
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}