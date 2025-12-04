package banco.services;

import banco.models.ListaDocumentos;
import java.util.List;
import java.util.Optional;

public interface ListaDocumentosService {
    List<ListaDocumentos> findAll();
    Optional<ListaDocumentos> findById(Integer id);
    ListaDocumentos save(ListaDocumentos listaDocumentos);
    ListaDocumentos update(ListaDocumentos listaDocumentos);
    void deleteById(Integer id);

    List<ListaDocumentos> findByUsuarioId(Integer idUsuario);
    List<ListaDocumentos> findByDocumentoId(Integer idDocumento);
}