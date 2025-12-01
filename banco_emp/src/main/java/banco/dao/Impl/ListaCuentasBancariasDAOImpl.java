package banco.dao.Impl;

import banco.dao.ListaCuentasBancariasDAO;
import banco.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ListaCuentasBancariasDAOImpl implements ListaCuentasBancariasDAO {

    private final JdbcTemplate jdbcTemplate;

    public ListaCuentasBancariasDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL con JOINs para obtener todos los datos relacionados
    private static final String SELECT_WITH_JOINS =
        "SELECT lcb.id, " +
        "lcb.id_cuenta, cb.n_cuenta, cb.n_intercuenta, cb.nombre AS cb_nombre, cb.saldo, " +
        "lcb.id_usuario, u.nombre AS u_nombre, u.apellidos AS u_apellidos, u.dni_ruc, u.correo " +
        "FROM lista_cuentasbancarias lcb " +
        "LEFT JOIN cuenta_bancaria cb ON lcb.id_cuenta = cb.id " +
        "LEFT JOIN t_usuario u ON lcb.id_usuario = u.id_usuario";

    // RowMapper personalizado
    private final RowMapper<ListaCuentasBancarias> listaCuentasRowMapper = (rs, rowNum) -> {
        ListaCuentasBancarias lcb = new ListaCuentasBancarias();
        lcb.setId(rs.getInt("id"));

        // Mapear CuentaBancaria
        if (rs.getObject("id_cuenta") != null) {
            CuentaBancaria cb = new CuentaBancaria();
            cb.setId(rs.getInt("id_cuenta"));
            cb.setNCuenta(rs.getString("n_cuenta"));
            cb.setN_intercuenta(rs.getString("n_intercuenta"));
            cb.setNombre(rs.getString("cb_nombre"));
            cb.setSaldo(rs.getBigDecimal("saldo"));
            lcb.setCuentaBancaria(cb);
        }

        // Mapear Usuario
        if (rs.getObject("id_usuario") != null) {
            Usuario u = new Usuario();
            u.setId_usuario(rs.getInt("id_usuario"));
            u.setNombre(rs.getString("u_nombre"));
            u.setApellidos(rs.getString("u_apellidos"));
            u.setDni_ruc(rs.getString("dni_ruc"));
            u.setCorreo(rs.getString("correo"));
            lcb.setUsuario(u);
        }

        return lcb;
    };

    @Override
    public List<ListaCuentasBancarias> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOINS, listaCuentasRowMapper);
    }

    @Override
    public Optional<ListaCuentasBancarias> findById(Integer id) {
        String sql = SELECT_WITH_JOINS + " WHERE lcb.id = ?";
        List<ListaCuentasBancarias> result = jdbcTemplate.query(sql, listaCuentasRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public ListaCuentasBancarias save(ListaCuentasBancarias listaCuentasBancarias) {
        String sql = "INSERT INTO lista_cuentasbancarias (id_cuenta, id_usuario) VALUES (?, ?)";

        jdbcTemplate.update(sql,
                listaCuentasBancarias.getCuentaBancaria().getId(),
                listaCuentasBancarias.getUsuario().getId_usuario());
        return listaCuentasBancarias;
    }

    @Override
    public ListaCuentasBancarias update(ListaCuentasBancarias listaCuentasBancarias) {
        String sql = "UPDATE lista_cuentasbancarias SET id_cuenta=?, id_usuario=? WHERE id=?";

        jdbcTemplate.update(sql,
                listaCuentasBancarias.getCuentaBancaria().getId(),
                listaCuentasBancarias.getUsuario().getId_usuario(),
                listaCuentasBancarias.getId());
        return listaCuentasBancarias;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM lista_cuentasbancarias WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}