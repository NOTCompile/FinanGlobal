package banco.services.Impl;

import banco.dao.EstadoDAO;
import banco.models.Estado;
import banco.services.EstadoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EstadoServiceImpl implements EstadoService {

    private final EstadoDAO estadoDAO;

    public EstadoServiceImpl(EstadoDAO estadoDAO) {
        this.estadoDAO = estadoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estado> findAll() {
        return estadoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estado> findById(Integer id) {
        return estadoDAO.findById(id);
    }

    @Override
    @Transactional
    public Estado save(Estado estado) {
        return estadoDAO.save(estado);
    }

    @Override
    @Transactional
    public Estado update(Estado estado) {
        if (estadoDAO.findById(estado.getId()).isEmpty()) {
            throw new RuntimeException("Estado no encontrado para actualizar.");
        }
        return estadoDAO.update(estado);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        estadoDAO.deleteById(id);
    }
}