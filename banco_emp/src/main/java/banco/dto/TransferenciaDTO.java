package banco.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

// Se asume que las entidades Usuario y CuentaBancaria ya están disponibles en los servicios.
public class TransferenciaDTO {

    private BigDecimal monto;

    @JsonProperty("nCuentaEmisora")
    private String nCuentaEmisora;

    @JsonProperty("nCuentaReceptora")
    private String nCuentaReceptora;

    @JsonProperty("idTipoTransferencia")
    private Integer idTipoTransferencia;

    // Opcionalmente, puedes incluir el ID del usuario logueado (Emisor) aquí, o tomarlo del contexto de seguridad.

    // Getters y Setters

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getNCuentaEmisora() {
        return nCuentaEmisora;
    }

    public void setNCuentaEmisora(String nCuentaEmisora) {
        this.nCuentaEmisora = nCuentaEmisora;
    }

    public String getNCuentaReceptora() {
        return nCuentaReceptora;
    }

    public void setNCuentaReceptora(String nCuentaReceptora) {
        this.nCuentaReceptora = nCuentaReceptora;
    }

    public Integer getIdTipoTransferencia() {
        return idTipoTransferencia;
    }

    public void setIdTipoTransferencia(Integer idTipoTransferencia) {
        this.idTipoTransferencia = idTipoTransferencia;
    }
}