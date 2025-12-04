package banco.dao;

import banco.models.Estado;
import java.util.List;
import java.util.Optional;

public interface EstadoDAO {
    List<Estado> findAll();
    Optional<Estado> findById(Integer id);
    Estado save(Estado estado);
    Estado update(Estado estado);
    void deleteById(Integer id);
}