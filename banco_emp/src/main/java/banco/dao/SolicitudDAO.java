package banco.dao;

import banco.models.Solicitud;
import java.util.List;
import java.util.Optional;

public interface SolicitudDAO {
    List<Solicitud> findAll();
    Optional<Solicitud> findById(Integer id);
    Solicitud save(Solicitud solicitud);
    Solicitud update(Solicitud solicitud);
    void deleteById(Integer id);
    List<Solicitud> findByProductoId(Integer idProducto);
}

