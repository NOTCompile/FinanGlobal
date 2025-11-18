package banco.dto; // Asegúrate de que este paquete exista

public class ListaDocumentosDTO {

    private Integer idUsuario;
    private Integer idDocumento;

    // Constructor vacío
    public ListaDocumentosDTO() {
    }

    // Getters y Setters

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }
}