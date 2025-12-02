package banco.services.Impl;

import banco.dao.ReclamoDAO;
import banco.models.Reclamo;
import banco.services.ReclamoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ReclamoServiceImpl implements ReclamoService {

    private final ReclamoDAO reclamoDAO;

    public ReclamoServiceImpl(ReclamoDAO reclamoDAO) {
        this.reclamoDAO = reclamoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reclamo> findAll() {
        return reclamoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reclamo> findById(Integer id) {
        return reclamoDAO.findById(id);
    }

    @Override
    @Transactional
    public Reclamo save(Reclamo reclamo) {
        return reclamoDAO.save(reclamo);
    }

    @Override
    @Transactional
    public Reclamo update(Reclamo reclamo) {
        return reclamoDAO.update(reclamo);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        reclamoDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reclamo> findByUsuarioId(Integer idUsuario) {
        return reclamoDAO.findByUsuarioId(idUsuario);
    }
}

