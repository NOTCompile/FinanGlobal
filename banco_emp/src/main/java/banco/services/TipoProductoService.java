package banco.services;

import banco.models.TipoProducto;
import java.util.List;
import java.util.Optional;

public interface TipoProductoService {
    List<TipoProducto> findAll();
    Optional<TipoProducto> findById(Integer id);
    TipoProducto save(TipoProducto tipoProducto);
    TipoProducto update(TipoProducto tipoProducto);
    void deleteById(Integer id);
}