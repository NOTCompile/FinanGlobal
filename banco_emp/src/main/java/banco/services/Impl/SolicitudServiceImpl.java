package banco.services.Impl;

import banco.dao.SolicitudDAO;
import banco.models.Solicitud;
import banco.services.SolicitudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudDAO solicitudDAO;

    public SolicitudServiceImpl(SolicitudDAO solicitudDAO) {
        this.solicitudDAO = solicitudDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Solicitud> findAll() {
        return solicitudDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Solicitud> findById(Integer id) {
        return solicitudDAO.findById(id);
    }

    @Override
    @Transactional
    public Solicitud save(Solicitud solicitud) {
        return solicitudDAO.save(solicitud);
    }

    @Override
    @Transactional
    public Solicitud update(Solicitud solicitud) {
        return solicitudDAO.update(solicitud);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        solicitudDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Solicitud> findByProductoId(Integer idProducto) {
        return solicitudDAO.findByProductoId(idProducto);
    }
}

