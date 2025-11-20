package banco.services.Impl;

import banco.dao.TipoProductoDAO;
import banco.models.TipoProducto;
import banco.services.TipoProductoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoProductoServiceImpl implements TipoProductoService {

    private final TipoProductoDAO tipoProductoDAO;

    public TipoProductoServiceImpl(TipoProductoDAO tipoProductoDAO) {
        this.tipoProductoDAO = tipoProductoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoProducto> findAll() {
        return tipoProductoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoProducto> findById(Integer id) {
        return tipoProductoDAO.findById(id);
    }

    @Override
    @Transactional
    public TipoProducto save(TipoProducto tipoProducto) {
        return tipoProductoDAO.save(tipoProducto);
    }

    @Override
    @Transactional
    public TipoProducto update(TipoProducto tipoProducto) {
        if (tipoProductoDAO.findById(tipoProducto.getId()).isEmpty()) {
            throw new RuntimeException("Tipo de Producto no encontrado para actualizar.");
        }
        return tipoProductoDAO.update(tipoProducto);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        tipoProductoDAO.deleteById(id);
    }
}