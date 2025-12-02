package banco.dao.Impl;

import banco.dao.TarjetaDAO;
import banco.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TarjetaDAOImpl implements TarjetaDAO {

    private final JdbcTemplate jdbcTemplate;

    public TarjetaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_WITH_JOINS =
        "SELECT t.id, t.codigo, t.cvc, t.fecha_caducidad, " +
        "t.id_cuenta, cb.n_cuenta, cb.n_intercuenta, cb.nombre AS cb_nombre, cb.saldo, " +
        "t.id_tipo, tt.tipo AS tt_nombre, tt.descripcion AS tt_descripcion " +
        "FROM tarjetas t " +
        "LEFT JOIN cuenta_bancaria cb ON t.id_cuenta = cb.id " +
        "LEFT JOIN tipo_tarjeta tt ON t.id_tipo = tt.id";

    private final RowMapper<Tarjeta> tarjetaRowMapper = (rs, rowNum) -> {
        Tarjeta t = new Tarjeta();
        t.setId(rs.getInt("id"));
        t.setCodigo(rs.getString("codigo"));
        t.setCvc(rs.getString("cvc"));
        t.setFechaCaducidad(rs.getDate("fecha_caducidad") != null ? rs.getDate("fecha_caducidad").toLocalDate() : null);

        if (rs.getObject("id_cuenta") != null) {
            CuentaBancaria cb = new CuentaBancaria();
            cb.setId(rs.getInt("id_cuenta"));
            cb.setNCuenta(rs.getString("n_cuenta"));
            cb.setN_intercuenta(rs.getString("n_intercuenta"));
            cb.setNombre(rs.getString("cb_nombre"));
            cb.setSaldo(rs.getBigDecimal("saldo"));
            t.setCuentaBancaria(cb);
        }

        if (rs.getObject("id_tipo") != null) {
            TipoTarjeta tt = new TipoTarjeta();
            tt.setId(rs.getInt("id_tipo"));
            tt.setTipo(rs.getString("tt_nombre"));
            tt.setDescripcion(rs.getString("tt_descripcion"));
            t.setTipoTarjeta(tt);
        }

        return t;
    };

    @Override
    public List<Tarjeta> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOINS, tarjetaRowMapper);
    }

    @Override
    public Optional<Tarjeta> findById(Integer id) {
        String sql = SELECT_WITH_JOINS + " WHERE t.id = ?";
        List<Tarjeta> result = jdbcTemplate.query(sql, tarjetaRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Tarjeta save(Tarjeta tarjeta) {
        String sql = "INSERT INTO tarjetas (codigo, cvc, fecha_caducidad, id_cuenta, id_tipo) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                tarjeta.getCodigo(),
                tarjeta.getCvc(),
                tarjeta.getFechaCaducidad(),
                tarjeta.getCuentaBancaria() != null ? tarjeta.getCuentaBancaria().getId() : null,
                tarjeta.getTipoTarjeta() != null ? tarjeta.getTipoTarjeta().getId() : null);
        return tarjeta;
    }

    @Override
    public Tarjeta update(Tarjeta tarjeta) {
        String sql = "UPDATE tarjetas SET codigo=?, cvc=?, fecha_caducidad=?, id_cuenta=?, id_tipo=? WHERE id=?";
        jdbcTemplate.update(sql,
                tarjeta.getCodigo(),
                tarjeta.getCvc(),
                tarjeta.getFechaCaducidad(),
                tarjeta.getCuentaBancaria() != null ? tarjeta.getCuentaBancaria().getId() : null,
                tarjeta.getTipoTarjeta() != null ? tarjeta.getTipoTarjeta().getId() : null,
                tarjeta.getId());
        return tarjeta;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM tarjetas WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Tarjeta> findByCuentaId(Integer idCuenta) {
        String sql = SELECT_WITH_JOINS + " WHERE t.id_cuenta = ?";
        return jdbcTemplate.query(sql, tarjetaRowMapper, idCuenta);
    }
}

