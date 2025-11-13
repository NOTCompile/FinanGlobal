package banco.services;

import banco.models.TipoDocumento;
import java.util.List;
import java.util.Optional;

public interface TipoDocumentoService {
    List<TipoDocumento> findAll();
    Optional<TipoDocumento> findById(Integer id);
    TipoDocumento save(TipoDocumento tipoDocumento);
    TipoDocumento update(TipoDocumento tipoDocumento);
    void deleteById(Integer id);
}