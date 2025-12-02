package banco.services.Impl;

import banco.dao.TipoReclamoDAO;
import banco.models.TipoReclamo;
import banco.services.TipoReclamoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoReclamoServiceImpl implements TipoReclamoService {

    private final TipoReclamoDAO tipoReclamoDAO;

    public TipoReclamoServiceImpl(TipoReclamoDAO tipoReclamoDAO) {
        this.tipoReclamoDAO = tipoReclamoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoReclamo> findAll() {
        return tipoReclamoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoReclamo> findById(Integer id) {
        return tipoReclamoDAO.findById(id);
    }

    @Override
    @Transactional
    public TipoReclamo save(TipoReclamo tipoReclamo) {
        return tipoReclamoDAO.save(tipoReclamo);
    }

    @Override
    @Transactional
    public TipoReclamo update(TipoReclamo tipoReclamo) {
        return tipoReclamoDAO.update(tipoReclamo);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        tipoReclamoDAO.deleteById(id);
    }
}

