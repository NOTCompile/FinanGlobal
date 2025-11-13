package banco.services.Impl;

import banco.dao.TipoDocumentoDAO;
import banco.models.TipoDocumento;
import banco.services.TipoDocumentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private final TipoDocumentoDAO tipoDocumentoDAO;

    public TipoDocumentoServiceImpl(TipoDocumentoDAO tipoDocumentoDAO) {
        this.tipoDocumentoDAO = tipoDocumentoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDocumento> findAll() {
        return tipoDocumentoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDocumento> findById(Integer id) {
        return tipoDocumentoDAO.findById(id);
    }

    @Override
    @Transactional
    public TipoDocumento save(TipoDocumento tipoDocumento) {
        // Lógica de negocio antes de guardar, ej: Normalizar el campo 'tipo'
        return tipoDocumentoDAO.save(tipoDocumento);
    }

    @Override
    @Transactional
    public TipoDocumento update(TipoDocumento tipoDocumento) {
        if (tipoDocumentoDAO.findById(tipoDocumento.getId()).isEmpty()) {
            throw new RuntimeException("Tipo de Documento no encontrado para actualizar.");
        }
        return tipoDocumentoDAO.update(tipoDocumento);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // Lógica de negocio: Verificar que ningún Documento use este tipo antes de eliminar.
        tipoDocumentoDAO.deleteById(id);
    }
}