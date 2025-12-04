package banco.dao;

import banco.models.Transferencia;
import java.util.List;
import java.util.Optional;

public interface TransferenciaDAO {
    List<Transferencia> findAll();
    Optional<Transferencia> findById(Integer id);
    Transferencia save(Transferencia transferencia);
    // Métodos específicos para transferencias por cuenta emisora/receptora
    List<Transferencia> findByCuentaEmisoraId(Integer idCuenta);
    List<Transferencia> findByCuentaReceptoraId(Integer idCuenta);
}