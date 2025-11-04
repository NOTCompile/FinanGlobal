package banco.services;

import banco.models.CuentaBancaria;
import java.util.List;
import java.util.Optional;

public interface CuentaBancariaService {
    List<CuentaBancaria> findAll();

    Optional<CuentaBancaria> findById(Integer id);

    CuentaBancaria save(CuentaBancaria cuenta);

    CuentaBancaria update(CuentaBancaria cuenta);

    void deleteById(Integer id);
}
