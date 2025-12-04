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

        // DEBUG: Imprimir datos recibidos
        System.out.println("=== DEBUG TRANSFERENCIA ===");
        System.out.println("Monto: " + dto.getMonto());
        System.out.println("Cuenta Emisora recibida: '" + dto.getNCuentaEmisora() + "'");
        System.out.println("Cuenta Receptora recibida: '" + dto.getNCuentaReceptora() + "'");
        System.out.println("Tipo Transferencia ID: " + dto.getIdTipoTransferencia());

        // 1. Validaciones iniciales
        if (dto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto debe ser positivo.");
        }

        // 2. Obtener Cuentas
        System.out.println("Buscando cuenta emisora con número: '" + dto.getNCuentaEmisora() + "'");
        CuentaBancaria cuentaEmisora = cuentaBancariaService.findByNCuenta(dto.getNCuentaEmisora())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta emisora no encontrada."));

        System.out.println("✓ Cuenta emisora encontrada: ID=" + cuentaEmisora.getId() + ", Nombre=" + cuentaEmisora.getNombre());

        System.out.println("Buscando cuenta receptora con número: '" + dto.getNCuentaReceptora() + "'");
        CuentaBancaria cuentaReceptora = cuentaBancariaService.findByNCuenta(dto.getNCuentaReceptora())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta receptora no encontrada."));

        System.out.println("✓ Cuenta receptora encontrada: ID=" + cuentaReceptora.getId() + ", Nombre=" + cuentaReceptora.getNombre());

        // 3. Obtener Tipo de Transferencia
        TipoTransferencia tipoTransferencia = tipoTransferenciaService.findById(dto.getIdTipoTransferencia())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de transferencia no válido."));


        // 4. Lógica de Fondos y Saldo

        // a) Verificar saldo
        System.out.println("Verificando saldo. Saldo emisora: " + cuentaEmisora.getSaldo() + ", Monto a transferir: " + dto.getMonto());
        if (cuentaEmisora.getSaldo().compareTo(dto.getMonto()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente en la cuenta emisora.");
        }

        // b) Realizar Débito
        BigDecimal nuevoSaldoEmisor = cuentaEmisora.getSaldo().subtract(dto.getMonto());
        System.out.println("Actualizando saldo emisora de " + cuentaEmisora.getSaldo() + " a " + nuevoSaldoEmisor);
        cuentaEmisora.setSaldo(nuevoSaldoEmisor);

        try {
            cuentaBancariaService.update(cuentaEmisora);
            System.out.println("✓ Saldo emisora actualizado correctamente");
        } catch (Exception e) {
            System.err.println("ERROR al actualizar cuenta emisora: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar cuenta emisora: " + e.getMessage());
        }

        // c) Realizar Crédito
        BigDecimal nuevoSaldoReceptor = cuentaReceptora.getSaldo().add(dto.getMonto());
        System.out.println("Actualizando saldo receptora de " + cuentaReceptora.getSaldo() + " a " + nuevoSaldoReceptor);
        cuentaReceptora.setSaldo(nuevoSaldoReceptor);

        try {
            cuentaBancariaService.update(cuentaReceptora);
            System.out.println("✓ Saldo receptora actualizado correctamente");
        } catch (Exception e) {
            System.err.println("ERROR al actualizar cuenta receptora: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar cuenta receptora: " + e.getMessage());
        }

        // 5. Registrar Transferencia
        System.out.println("Registrando transferencia...");
        Transferencia transferencia = new Transferencia();
        transferencia.setMonto(dto.getMonto());
        transferencia.setFecha(LocalDate.now());

        // Asignar relaciones (asumiendo que las cuentas tienen el objeto Usuario relacionado)
        transferencia.setUsuarioEmisor(cuentaEmisora.getUsuario());
        transferencia.setCuentaEmisora(cuentaEmisora);
        transferencia.setUsuarioReceptor(cuentaReceptora.getUsuario());
        transferencia.setCuentaReceptora(cuentaReceptora);
        transferencia.setTipoTransferencia(tipoTransferencia);

        try {
            Transferencia resultado = transferenciaDAO.save(transferencia);
            System.out.println("✓ Transferencia guardada con ID: " + resultado.getId());
            return resultado;
        } catch (Exception e) {
            System.err.println("ERROR al guardar transferencia: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar transferencia: " + e.getMessage());
        }
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