package banco.services;

import banco.models.Transferencia;
import banco.dto.TransferenciaDTO;
import java.util.List;
import java.util.Optional;

public interface TransferenciaService {
    // Método principal que ejecuta la lógica de negocio
    Transferencia realizarTransferencia(TransferenciaDTO dto);

    // Métodos CRUD estándar
    List<Transferencia> findAll();
    Optional<Transferencia> findById(Integer id);
    // ...
}