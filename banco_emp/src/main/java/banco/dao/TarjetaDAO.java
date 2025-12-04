package banco.dao;

import banco.models.Tarjeta;
import java.util.List;
import java.util.Optional;

public interface TarjetaDAO {
    List<Tarjeta> findAll();
    Optional<Tarjeta> findById(Integer id);
    Tarjeta save(Tarjeta tarjeta);
    Tarjeta update(Tarjeta tarjeta);
    void deleteById(Integer id);
    List<Tarjeta> findByCuentaId(Integer idCuenta);
}

