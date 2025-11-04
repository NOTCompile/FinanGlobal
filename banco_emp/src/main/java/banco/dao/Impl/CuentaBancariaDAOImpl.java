package banco.dao.Impl;


import banco.dao.CuentaBancariaDAO;
import banco.models.CuentaBancaria;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public class CuentaBancariaDAOImpl implements CuentaBancariaDAO {

    // Asumiendo que has inyectado JdbcTemplate correctamente

    private final JdbcTemplate jdbcTemplate;

    public CuentaBancariaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CuentaBancaria> findAll() {
        // CORRECCIÓN: Seleccionar todas las columnas EXACTAS de la tabla para que BeanPropertyRowMapper funcione.
        // También debes asegurarte de que el modelo CuentaBancaria tenga getters/setters que
        // coincidan con estas columnas o usar @Column en el modelo.
        String sql = "SELECT id, id_usuario, nombre, n_cuenta, n_intercuenta, saldo FROM public.cuenta_bancaria";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CuentaBancaria.class));
    }

    @Override
    public Optional<CuentaBancaria> findById(Integer id) {
        // CORRECCIÓN: Mismo SELECT que findAll.
        String sql = "SELECT id, id_usuario, nombre, n_cuenta, n_intercuenta, saldo FROM public.cuenta_bancaria WHERE id = ?";
        try {
            CuentaBancaria c = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CuentaBancaria.class), id);
            return Optional.ofNullable(c);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public CuentaBancaria save(CuentaBancaria cuenta) {
        // CORRECCIÓN: Los nombres de las columnas SQL deben ser las que están en la DB.
        // Se asume que has añadido 'banco' y 'tipo_cuenta' a la BD como resolvimos antes.
        // También se incluye 'nombre' y 'n_intercuenta' si son relevantes para el INSERT.
        String sql = "INSERT INTO public.cuenta_bancaria (nombre, n_cuenta, n_intercuenta, saldo, id_usuario) " +
                "VALUES (?, ?, ?, ?, ?)"; // Hay 5 campos para insertar

        jdbcTemplate.update(sql,
                cuenta.getNombre(),
                cuenta.getNumero_cuenta(),
                cuenta.getN_intercuenta(),
                cuenta.getSaldo(),
                cuenta.getUsuario().getId_usuario()); // Hay 5 parámetros
        return cuenta;
    }

    @Override
    public CuentaBancaria update(CuentaBancaria cuenta) {
        // CORRECCIÓN: Los nombres de las columnas SQL deben ser las que están en la DB.
        String sql = "UPDATE public.cuenta_bancaria SET nombre=?, n_cuenta=?, n_intercuenta=?, saldo=?, id_usuario=? WHERE id=?";

        jdbcTemplate.update(sql,
                cuenta.getNombre(),
                cuenta.getNumero_cuenta(),
                cuenta.getN_intercuenta(),
                cuenta.getSaldo(),
                cuenta.getUsuario().getId_usuario(),
                cuenta.getId());
        return cuenta;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM public.cuenta_bancaria WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
