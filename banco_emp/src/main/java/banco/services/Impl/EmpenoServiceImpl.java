package banco.services.Impl;

import banco.dao.EmpenoDAO;
import banco.models.Empeno;
import banco.services.EmpenoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EmpenoServiceImpl implements EmpenoService {

    private final EmpenoDAO empenoDAO;

    public EmpenoServiceImpl(EmpenoDAO empenoDAO) {
        this.empenoDAO = empenoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empeno> findAll() {
        return empenoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empeno> findById(Integer id) {
        return empenoDAO.findById(id);
    }

    @Override
    @Transactional
    public Empeno save(Empeno empeno) {
        // Aquí podrías agregar lógica de negocio antes de guardar, ej:
        // * Calcular automáticamente valorRecuperacion basado en valorPrestado.
        return empenoDAO.save(empeno);
    }

    @Override
    @Transactional
    public Empeno update(Empeno empeno) {
        if (empenoDAO.findById(empeno.getId()).isEmpty()) {
            throw new RuntimeException("Empeño no encontrado para actualizar.");
        }
        // Lógica de negocio antes de actualizar
        return empenoDAO.update(empeno);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // Lógica de negocio antes de eliminar, ej:
        // * No permitir eliminar si el empeño aún no ha finalizado.
        empenoDAO.deleteById(id);
    }
}