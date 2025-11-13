package banco.services.Impl;

import banco.dao.ProductoDAO;
import banco.models.Producto;
import banco.services.ProductoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoDAO productoDAO;

    public ProductoServiceImpl(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findById(Integer id) {
        return productoDAO.findById(id);
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productoDAO.save(producto);
    }

    @Override
    @Transactional
    public Producto update(Producto producto) {
        if (productoDAO.findById(producto.getId()).isEmpty()) {
            throw new RuntimeException("Producto no encontrado para actualizar.");
        }
        return productoDAO.update(producto);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        productoDAO.deleteById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByUsuarioId(Integer idUsuario) {
        // Lógica de negocio aquí, si es necesario, antes de llamar al DAO
        return productoDAO.findByUsuarioId(idUsuario);
    }
}