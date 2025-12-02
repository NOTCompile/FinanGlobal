package banco.dao.Impl;

import banco.dao.CuentaBancariaDAO;
import banco.models.CuentaBancaria;
import banco.models.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class CuentaBancariaDAOImpl implements CuentaBancariaDAO {

    private final JdbcTemplate jdbcTemplate;

    public CuentaBancariaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL con JOIN para obtener todos los datos del usuario
    private static final String SELECT_WITH_JOIN =
        "SELECT cb.id, cb.nombre, cb.n_cuenta, cb.n_intercuenta, cb.saldo, " +
        "cb.id_usuario, u.nombre AS u_nombre, u.apellidos AS u_apellidos, u.dni_ruc, u.correo, " +
        "u.sexo, u.telefono, u.direccion " +
        "FROM cuenta_bancaria cb " +
        "LEFT JOIN t_usuario u ON cb.id_usuario = u.id_usuario";

    // RowMapper personalizado
    private final RowMapper<CuentaBancaria> cuentaBancariaRowMapper = (rs, rowNum) -> {
        CuentaBancaria cb = new CuentaBancaria();
        cb.setId(rs.getInt("id"));
        cb.setNombre(rs.getString("nombre"));
        cb.setNCuenta(rs.getString("n_cuenta"));
        cb.setN_intercuenta(rs.getString("n_intercuenta"));
        cb.setSaldo(rs.getBigDecimal("saldo"));

        // Mapear Usuario completo
        if (rs.getObject("id_usuario") != null) {
            Usuario u = new Usuario();
            u.setId_usuario(rs.getInt("id_usuario"));
            u.setNombre(rs.getString("u_nombre"));
            u.setApellidos(rs.getString("u_apellidos"));
            u.setDni_ruc(rs.getString("dni_ruc"));
            u.setCorreo(rs.getString("correo"));
            u.setSexo(rs.getString("sexo"));
            u.setTelefono(rs.getString("telefono"));
            u.setDireccion(rs.getString("direccion"));
            cb.setUsuario(u);
        }

        return cb;
    };

    @Override
    public List<CuentaBancaria> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOIN, cuentaBancariaRowMapper);
    }

    @Override
    public Optional<CuentaBancaria> findById(Integer id) {
        String sql = SELECT_WITH_JOIN + " WHERE cb.id = ?";
        List<CuentaBancaria> result = jdbcTemplate.query(sql, cuentaBancariaRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Optional<CuentaBancaria> findByNCuenta(String nCuenta) {
        String sql = SELECT_WITH_JOIN + " WHERE cb.n_cuenta = ?";
        List<CuentaBancaria> result = jdbcTemplate.query(sql, cuentaBancariaRowMapper, nCuenta);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public CuentaBancaria save(CuentaBancaria cuenta) {
        String sql = "INSERT INTO cuenta_bancaria (nombre, n_cuenta, n_intercuenta, saldo, id_usuario) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                cuenta.getNombre(),
                cuenta.getNCuenta(),
                cuenta.getN_intercuenta(),
                cuenta.getSaldo(),
                cuenta.getUsuario().getId_usuario());
        return cuenta;
    }

    @Override
    public CuentaBancaria update(CuentaBancaria cuenta) {
        String sql = "UPDATE cuenta_bancaria SET nombre=?, n_cuenta=?, n_intercuenta=?, saldo=?, id_usuario=? WHERE id=?";

        jdbcTemplate.update(sql,
                cuenta.getNombre(),
                cuenta.getNCuenta(),
                cuenta.getN_intercuenta(),
                cuenta.getSaldo(),
                cuenta.getUsuario().getId_usuario(),
                cuenta.getId());
        return cuenta;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM cuenta_bancaria WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

