package banco.services;

import banco.models.TipoReclamo;
import java.util.List;
import java.util.Optional;

public interface TipoReclamoService {
    List<TipoReclamo> findAll();
    Optional<TipoReclamo> findById(Integer id);
    TipoReclamo save(TipoReclamo tipoReclamo);
    TipoReclamo update(TipoReclamo tipoReclamo);
    void deleteById(Integer id);
}

