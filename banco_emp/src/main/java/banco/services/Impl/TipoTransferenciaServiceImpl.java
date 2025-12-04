package banco.services.Impl;

import banco.dao.TipoTransferenciaDAO;
import banco.models.TipoTransferencia;
import banco.services.TipoTransferenciaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoTransferenciaServiceImpl implements TipoTransferenciaService {

    private final TipoTransferenciaDAO tipoTransferenciaDAO;

    public TipoTransferenciaServiceImpl(TipoTransferenciaDAO tipoTransferenciaDAO) {
        this.tipoTransferenciaDAO = tipoTransferenciaDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoTransferencia> findAll() {
        return tipoTransferenciaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoTransferencia> findById(Integer id) {
        return tipoTransferenciaDAO.findById(id);
    }

    @Override
    @Transactional
    public TipoTransferencia save(TipoTransferencia tipoTransferencia) {
        // Lógica de negocio antes de guardar, ej: validar formato
        return tipoTransferenciaDAO.save(tipoTransferencia);
    }

    @Override
    @Transactional
    public TipoTransferencia update(TipoTransferencia tipoTransferencia) {
        if (tipoTransferenciaDAO.findById(tipoTransferencia.getId()).isEmpty()) {
            throw new RuntimeException("Tipo de Transferencia no encontrado para actualizar.");
        }
        return tipoTransferenciaDAO.update(tipoTransferencia);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // Lógica de negocio: Verificar si hay transferencias que usan este tipo antes de eliminar.
        tipoTransferenciaDAO.deleteById(id);
    }
}