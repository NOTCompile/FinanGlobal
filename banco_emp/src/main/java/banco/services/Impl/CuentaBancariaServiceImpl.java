package banco.services.Impl;


import banco.dao.CuentaBancariaDAO;
import banco.models.CuentaBancaria;
import banco.services.CuentaBancariaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service // Marca la clase como un componente de servicio
public class CuentaBancariaServiceImpl implements CuentaBancariaService {

    private final CuentaBancariaDAO cuentaBancariaDAO;

    // Inyección de dependencia por constructor (mejor práctica)
    public CuentaBancariaServiceImpl(CuentaBancariaDAO cuentaBancariaDAO) {
        this.cuentaBancariaDAO = cuentaBancariaDAO;
    }

    @Override
    @Transactional(readOnly = true) // Transacción solo lectura
    public List<CuentaBancaria> findAll() {
        return cuentaBancariaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaBancaria> findById(Integer id) {
        // Aquí podrías agregar lógica de negocio, como verificar permisos
        return cuentaBancariaDAO.findById(id);
    }
    // ⭐ Implementación del nuevo método en el Service ⭐
    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaBancaria> findByNCuenta(String nCuenta) {
        return cuentaBancariaDAO.findByNCuenta(nCuenta);
    }

    @Override
    @Transactional // Transacción de escritura
    public CuentaBancaria save(CuentaBancaria cuenta) {
        // Aquí va la lógica de negocio antes de guardar:
        // Ej: Validar que el nombre no sea nulo, establecer la fecha de creación, etc.
        return cuentaBancariaDAO.save(cuenta);
    }

    @Override
    @Transactional
    public CuentaBancaria update(CuentaBancaria cuenta) {
        // Aquí va la lógica para actualizar:
        // Ej: Verificar que la cuenta exista antes de llamar al DAO.
        if (cuentaBancariaDAO.findById(cuenta.getId()).isEmpty()) {
            throw new RuntimeException("Cuenta no encontrada para actualizar.");
        }
        return cuentaBancariaDAO.update(cuenta);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        // Lógica de negocio antes de eliminar:
        // Ej: Verificar que el saldo de la cuenta sea cero antes de eliminar.
        cuentaBancariaDAO.deleteById(id);
    }
}