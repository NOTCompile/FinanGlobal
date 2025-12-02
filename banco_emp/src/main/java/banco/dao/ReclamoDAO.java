package banco.dao;

import banco.models.Reclamo;
import java.util.List;
import java.util.Optional;

public interface ReclamoDAO {
    List<Reclamo> findAll();
    Optional<Reclamo> findById(Integer id);
    Reclamo save(Reclamo reclamo);
    Reclamo update(Reclamo reclamo);
    void deleteById(Integer id);
    List<Reclamo> findByUsuarioId(Integer idUsuario);
}

