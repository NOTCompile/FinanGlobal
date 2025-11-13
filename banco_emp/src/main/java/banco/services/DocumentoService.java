package banco.services;

import banco.models.Documento;
import java.util.List;
import java.util.Optional;

public interface DocumentoService {
    List<Documento> findAll();
    Optional<Documento> findById(Integer id);
    Documento save(Documento documento);
    Documento update(Documento documento);
    void deleteById(Integer id);
}