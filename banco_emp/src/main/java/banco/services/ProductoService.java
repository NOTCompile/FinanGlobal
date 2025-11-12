package banco.services;

import banco.models.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> findAll();
    Optional<Producto> findById(Integer id);
    Producto save(Producto producto);
    Producto update(Producto producto);
    void deleteById(Integer id);
}