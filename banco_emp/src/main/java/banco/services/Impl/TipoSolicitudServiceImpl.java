package banco.services.Impl;

import banco.dao.TipoSolicitudDAO;
import banco.models.TipoSolicitud;
import banco.services.TipoSolicitudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoSolicitudServiceImpl implements TipoSolicitudService {

    private final TipoSolicitudDAO tipoSolicitudDAO;

    public TipoSolicitudServiceImpl(TipoSolicitudDAO tipoSolicitudDAO) {
        this.tipoSolicitudDAO = tipoSolicitudDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoSolicitud> findAll() {
        return tipoSolicitudDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoSolicitud> findById(Integer id) {
        return tipoSolicitudDAO.findById(id);
    }

    @Override
    @Transactional
    public TipoSolicitud save(TipoSolicitud tipoSolicitud) {
        return tipoSolicitudDAO.save(tipoSolicitud);
    }

    @Override
    @Transactional
    public TipoSolicitud update(TipoSolicitud tipoSolicitud) {
        return tipoSolicitudDAO.update(tipoSolicitud);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        tipoSolicitudDAO.deleteById(id);
    }
}

