package banco.services.Impl;

import banco.dao.TransferenciaDAO;
import banco.dto.TransferenciaDTO;
import banco.models.CuentaBancaria;
import banco.models.TipoTransferencia;
import banco.models.Transferencia;
import banco.services.TransferenciaService;
import banco.services.CuentaBancariaService;
import banco.services.TipoTransferenciaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    private final TransferenciaDAO transferenciaDAO;
    private final CuentaBancariaService cuentaBancariaService;
    private final TipoTransferenciaService tipoTransferenciaService;
    // NOTA: Se requeriría UsuarioService, pero se omiten para simplificar el ejemplo.

    public TransferenciaServiceImpl(
            TransferenciaDAO transferenciaDAO,
            CuentaBancariaService cuentaBancariaService,
            TipoTransferenciaService tipoTransferenciaService) {

        this.transferenciaDAO = transferenciaDAO;
        this.cuentaBancariaService = cuentaBancariaService;
        this.tipoTransferenciaService = tipoTransferenciaService;
    }

    @Override
    @Transactional // A-T-O-M-I-C-I-D-A-D: O todo se ejecuta, o nada se ejecuta.
    public Transferencia realizarTransferencia(TransferenciaDTO dto) {

        // 1. Validaciones iniciales
        if (dto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto debe ser positivo.");
        }

        // 2. Obtener Cuentas
        CuentaBancaria cuentaEmisora = cuentaBancariaService.findByNCuenta(dto.getNCuentaEmisora())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta emisora no encontrada."));

        CuentaBancaria cuentaReceptora = cuentaBancariaService.findByNCuenta(dto.getNCuentaReceptora())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta receptora no encontrada."));

        // 3. Obtener Tipo de Transferencia
        TipoTransferencia tipoTransferencia = tipoTransferenciaService.findById(dto.getIdTipoTransferencia())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de transferencia no válido."));


        // 4. Lógica de Fondos y Saldo

        // a) Verificar saldo
        if (cuentaEmisora.getSaldo().compareTo(dto.getMonto()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente en la cuenta emisora.");
        }

        // b) Realizar Débito
        BigDecimal nuevoSaldoEmisor = cuentaEmisora.getSaldo().subtract(dto.getMonto());
        cuentaEmisora.setSaldo(nuevoSaldoEmisor);
        cuentaBancariaService.update(cuentaEmisora); // O un método específico de actualización de saldo

        // c) Realizar Crédito
        BigDecimal nuevoSaldoReceptor = cuentaReceptora.getSaldo().add(dto.getMonto());
        cuentaReceptora.setSaldo(nuevoSaldoReceptor);
        cuentaBancariaService.update(cuentaReceptora); // O un método específico de actualización de saldo

        // 5. Registrar Transferencia
        Transferencia transferencia = new Transferencia();
        transferencia.setMonto(dto.getMonto());
        transferencia.setFecha(LocalDate.now());

        // Asignar relaciones (asumiendo que las cuentas tienen el objeto Usuario relacionado)
        transferencia.setUsuarioEmisor(cuentaEmisora.getUsuario());
        transferencia.setCuentaEmisora(cuentaEmisora);
        transferencia.setUsuarioReceptor(cuentaReceptora.getUsuario());
        transferencia.setCuentaReceptora(cuentaReceptora);
        transferencia.setTipoTransferencia(tipoTransferencia);

        return transferenciaDAO.save(transferencia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transferencia> findAll() {
        return transferenciaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transferencia> findById(Integer id) {
        return transferenciaDAO.findById(id);
    }
}