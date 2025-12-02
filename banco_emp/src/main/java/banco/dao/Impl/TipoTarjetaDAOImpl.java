package banco.dao.Impl;

import banco.dao.TipoTarjetaDAO;
import banco.models.TipoTarjeta;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TipoTarjetaDAOImpl implements TipoTarjetaDAO {

    private final JdbcTemplate jdbcTemplate;

    public TipoTarjetaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_ALL = "SELECT id, tipo, descripcion FROM tipo_tarjeta";

    private final RowMapper<TipoTarjeta> tipoTarjetaRowMapper = (rs, rowNum) -> {
        TipoTarjeta tt = new TipoTarjeta();
        tt.setId(rs.getInt("id"));
        tt.setTipo(rs.getString("tipo"));
        tt.setDescripcion(rs.getString("descripcion"));
        return tt;
    };

    @Override
    public List<TipoTarjeta> findAll() {
        return jdbcTemplate.query(SELECT_ALL, tipoTarjetaRowMapper);
    }

    @Override
    public Optional<TipoTarjeta> findById(Integer id) {
        String sql = SELECT_ALL + " WHERE id = ?";
        List<TipoTarjeta> result = jdbcTemplate.query(sql, tipoTarjetaRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public TipoTarjeta save(TipoTarjeta tipoTarjeta) {
        String sql = "INSERT INTO tipo_tarjeta (tipo, descripcion) VALUES (?, ?)";
        jdbcTemplate.update(sql, tipoTarjeta.getTipo(), tipoTarjeta.getDescripcion());
        return tipoTarjeta;
    }

    @Override
    public TipoTarjeta update(TipoTarjeta tipoTarjeta) {
        String sql = "UPDATE tipo_tarjeta SET tipo=?, descripcion=? WHERE id=?";
        jdbcTemplate.update(sql, tipoTarjeta.getTipo(), tipoTarjeta.getDescripcion(), tipoTarjeta.getId());
        return tipoTarjeta;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM tipo_tarjeta WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

