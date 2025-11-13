package banco.services.Impl;

import banco.dao.DocumentoDAO;
import banco.models.Documento;
import banco.services.DocumentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoDAO documentoDAO;

    public DocumentoServiceImpl(DocumentoDAO documentoDAO) {
        this.documentoDAO = documentoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Documento> findAll() {
        return documentoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Documento> findById(Integer id) {
        return documentoDAO.findById(id);
    }

    @Override
    @Transactional
    public Documento save(Documento documento) {
        // Lógica de negocio antes de guardar, ej: validar rutaArchivo
        return documentoDAO.save(documento);
    }

    @Override
    @Transactional
    public Documento update(Documento documento) {
        if (documentoDAO.findById(documento.getId()).isEmpty()) {
            throw new RuntimeException("Documento no encontrado para actualizar.");
        }
        return documentoDAO.update(documento);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // Lógica de negocio antes de eliminar, ej: verificar si está asociado a una lista
        documentoDAO.deleteById(id);
    }
}