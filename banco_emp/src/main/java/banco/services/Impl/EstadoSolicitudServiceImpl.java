package banco.services.Impl;

import banco.dao.EstadoSolicitudDAO;
import banco.models.EstadoSolicitud;
import banco.services.EstadoSolicitudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EstadoSolicitudServiceImpl implements EstadoSolicitudService {

    private final EstadoSolicitudDAO estadoSolicitudDAO;

    public EstadoSolicitudServiceImpl(EstadoSolicitudDAO estadoSolicitudDAO) {
        this.estadoSolicitudDAO = estadoSolicitudDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoSolicitud> findAll() {
        return estadoSolicitudDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoSolicitud> findById(Integer id) {
        return estadoSolicitudDAO.findById(id);
    }

    @Override
    @Transactional
    public EstadoSolicitud save(EstadoSolicitud estadoSolicitud) {
        // Lógica de negocio (ej: validar que el nombre no sea duplicado)
        return estadoSolicitudDAO.save(estadoSolicitud);
    }

    @Override
    @Transactional
    public EstadoSolicitud update(EstadoSolicitud estadoSolicitud) {
        if (estadoSolicitudDAO.findById(estadoSolicitud.getId()).isEmpty()) {
            throw new RuntimeException("Estado de Solicitud no encontrado para actualizar.");
        }
        return estadoSolicitudDAO.update(estadoSolicitud);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // Lógica de negocio antes de eliminar (ej: ¿Este estado está siendo usado por alguna solicitud activa?)
        estadoSolicitudDAO.deleteById(id);
    }
}