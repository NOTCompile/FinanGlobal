package banco.services.Impl;

import banco.dao.ListaDocumentosDAO;
import banco.models.ListaDocumentos;
import banco.services.ListaDocumentosService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ListaDocumentosServiceImpl implements ListaDocumentosService {

    private final ListaDocumentosDAO listaDocumentosDAO;

    public ListaDocumentosServiceImpl(ListaDocumentosDAO listaDocumentosDAO) {
        this.listaDocumentosDAO = listaDocumentosDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListaDocumentos> findAll() {
        return listaDocumentosDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListaDocumentos> findById(Integer id) {
        return listaDocumentosDAO.findById(id);
    }

    @Override
    @Transactional
    public ListaDocumentos save(ListaDocumentos listaDocumentos) {
        return listaDocumentosDAO.save(listaDocumentos);
    }

    @Override
    @Transactional
    public ListaDocumentos update(ListaDocumentos listaDocumentos) {
        if (listaDocumentosDAO.findById(listaDocumentos.getId()).isEmpty()) {
            throw new RuntimeException("Relación ListaDocumentos no encontrada para actualizar.");
        }
        return listaDocumentosDAO.update(listaDocumentos);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        listaDocumentosDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListaDocumentos> findByUsuarioId(Integer idUsuario) {
        return listaDocumentosDAO.findByUsuarioId(idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListaDocumentos> findByDocumentoId(Integer idDocumento) {
        return listaDocumentosDAO.findByDocumentoId(idDocumento);
    }
}