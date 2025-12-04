package banco.dao.Impl;

import banco.dao.CuentaBancariaDAO;
import banco.models.CuentaBancaria;
import banco.models.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
        System.out.println("=== DEBUG DAO findByNCuenta ===");
        System.out.println("SQL: " + sql);
        System.out.println("Parámetro nCuenta: '" + nCuenta + "'");
        System.out.println("Longitud del parámetro: " + (nCuenta != null ? nCuenta.length() : "null"));

        List<CuentaBancaria> result = jdbcTemplate.query(sql, cuentaBancariaRowMapper, nCuenta);

        System.out.println("Resultados encontrados: " + result.size());
        if (!result.isEmpty()) {
            CuentaBancaria cuenta = result.get(0);
            System.out.println("✓ Cuenta encontrada - ID: " + cuenta.getId() + ", NCuenta: '" + cuenta.getNCuenta() + "', Nombre: " + cuenta.getNombre());
        } else {
            System.out.println("✗ No se encontró ninguna cuenta con n_cuenta = '" + nCuenta + "'");
        }

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public CuentaBancaria save(CuentaBancaria cuenta) {
        String sql = "INSERT INTO cuenta_bancaria (nombre, n_cuenta, n_intercuenta, saldo, id_usuario) " +
                "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cuenta.getNombre());
            ps.setString(2, cuenta.getNCuenta());
            ps.setString(3, cuenta.getN_intercuenta());
            ps.setBigDecimal(4, cuenta.getSaldo());
            ps.setInt(5, cuenta.getUsuario().getId_usuario());
            return ps;
        }, keyHolder);

        // PostgreSQL retorna todas las columnas, obtenemos solo el id
        if (keyHolder.getKeys() != null && keyHolder.getKeys().containsKey("id")) {
            Number key = (Number) keyHolder.getKeys().get("id");
            cuenta.setId(key.intValue());
        }

        return cuenta;
    }

    @Override
    public CuentaBancaria update(CuentaBancaria cuenta) {
        System.out.println("=== DEBUG UPDATE CuentaBancaria ===");
        System.out.println("ID: " + cuenta.getId());
        System.out.println("Nombre: " + cuenta.getNombre());
        System.out.println("N_cuenta: " + cuenta.getNCuenta());
        System.out.println("Saldo: " + cuenta.getSaldo());
        System.out.println("Usuario: " + (cuenta.getUsuario() != null ? cuenta.getUsuario().getId_usuario() : "NULL"));

        if (cuenta.getUsuario() == null) {
            throw new RuntimeException("El objeto Usuario es null en CuentaBancaria ID: " + cuenta.getId());
        }

        String sql = "UPDATE cuenta_bancaria SET nombre=?, n_cuenta=?, n_intercuenta=?, saldo=?, id_usuario=? WHERE id=?";

        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    cuenta.getNombre(),
                    cuenta.getNCuenta(),
                    cuenta.getN_intercuenta(),
                    cuenta.getSaldo(),
                    cuenta.getUsuario().getId_usuario(),
                    cuenta.getId());

            System.out.println("Filas actualizadas: " + rowsAffected);

            if (rowsAffected == 0) {
                throw new RuntimeException("No se encontró cuenta con ID: " + cuenta.getId());
            }

            return cuenta;
        } catch (Exception e) {
            System.err.println("ERROR en UPDATE: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM cuenta_bancaria WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

