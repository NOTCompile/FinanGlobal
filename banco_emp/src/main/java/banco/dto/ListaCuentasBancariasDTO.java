package banco.dto;

public class ListaCuentasBancariasDTO {

    private Integer idCuenta; // id_cuenta
    private Integer idUsuario; // id_usuario

    // Constructor vacío
    public ListaCuentasBancariasDTO() {
    }

    // Getters y Setters

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}