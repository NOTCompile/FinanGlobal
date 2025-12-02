package banco.dao;

import banco.models.TipoSolicitud;
import java.util.List;
import java.util.Optional;

public interface TipoSolicitudDAO {
    List<TipoSolicitud> findAll();
    Optional<TipoSolicitud> findById(Integer id);
    TipoSolicitud save(TipoSolicitud tipoSolicitud);
    TipoSolicitud update(TipoSolicitud tipoSolicitud);
    void deleteById(Integer id);
}

