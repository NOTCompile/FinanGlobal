package banco.services;

import banco.models.Estado;
import java.util.List;
import java.util.Optional;

public interface EstadoService {
    List<Estado> findAll();
    Optional<Estado> findById(Integer id);
    Estado save(Estado estado);
    Estado update(Estado estado);
    void deleteById(Integer id);
}