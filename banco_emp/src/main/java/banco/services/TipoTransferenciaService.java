package banco.services;

import banco.models.TipoTransferencia;
import java.util.List;
import java.util.Optional;

public interface TipoTransferenciaService {
    List<TipoTransferencia> findAll();
    Optional<TipoTransferencia> findById(Integer id);
    TipoTransferencia save(TipoTransferencia tipoTransferencia);
    TipoTransferencia update(TipoTransferencia tipoTransferencia);
    void deleteById(Integer id);
}