package banco.dao;

import banco.models.EstadoSolicitud;
import java.util.List;
import java.util.Optional;

public interface EstadoSolicitudDAO {
    List<EstadoSolicitud> findAll();
    Optional<EstadoSolicitud> findById(Integer id);
    EstadoSolicitud save(EstadoSolicitud estadoSolicitud);
    EstadoSolicitud update(EstadoSolicitud estadoSolicitud);
    void deleteById(Integer id);
}