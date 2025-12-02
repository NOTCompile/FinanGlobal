package banco.dao;

import banco.models.TipoTarjeta;
import java.util.List;
import java.util.Optional;

public interface TipoTarjetaDAO {
    List<TipoTarjeta> findAll();
    Optional<TipoTarjeta> findById(Integer id);
    TipoTarjeta save(TipoTarjeta tipoTarjeta);
    TipoTarjeta update(TipoTarjeta tipoTarjeta);
    void deleteById(Integer id);
}

