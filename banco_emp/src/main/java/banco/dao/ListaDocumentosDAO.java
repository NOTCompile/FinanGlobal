package banco.dao;

import banco.models.ListaDocumentos;
import java.util.List;
import java.util.Optional;

public interface ListaDocumentosDAO {
    List<ListaDocumentos> findAll();
    Optional<ListaDocumentos> findById(Integer id);
    ListaDocumentos save(ListaDocumentos listaDocumentos);
    ListaDocumentos update(ListaDocumentos listaDocumentos);
    void deleteById(Integer id);

    // Método útil para buscar por ID de Usuario o Documento
    List<ListaDocumentos> findByUsuarioId(Integer idUsuario);
    List<ListaDocumentos> findByDocumentoId(Integer idDocumento);
}