package banco.dao.Impl;

import banco.dao.TipoReclamoDAO;
import banco.models.TipoReclamo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TipoReclamoDAOImpl implements TipoReclamoDAO {

    private final JdbcTemplate jdbcTemplate;

    public TipoReclamoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_ALL = "SELECT id, nombre, descripcion FROM tipo_reclamo";

    private final RowMapper<TipoReclamo> tipoReclamoRowMapper = (rs, rowNum) -> {
        TipoReclamo tr = new TipoReclamo();
        tr.setId(rs.getInt("id"));
        tr.setNombre(rs.getString("nombre"));
        tr.setDescripcion(rs.getString("descripcion"));
        return tr;
    };

    @Override
    public List<TipoReclamo> findAll() {
        return jdbcTemplate.query(SELECT_ALL, tipoReclamoRowMapper);
    }

    @Override
    public Optional<TipoReclamo> findById(Integer id) {
        String sql = SELECT_ALL + " WHERE id = ?";
        List<TipoReclamo> result = jdbcTemplate.query(sql, tipoReclamoRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public TipoReclamo save(TipoReclamo tipoReclamo) {
        String sql = "INSERT INTO tipo_reclamo (nombre, descripcion) VALUES (?, ?)";
        jdbcTemplate.update(sql, tipoReclamo.getNombre(), tipoReclamo.getDescripcion());
        return tipoReclamo;
    }

    @Override
    public TipoReclamo update(TipoReclamo tipoReclamo) {
        String sql = "UPDATE tipo_reclamo SET nombre=?, descripcion=? WHERE id=?";
        jdbcTemplate.update(sql, tipoReclamo.getNombre(), tipoReclamo.getDescripcion(), tipoReclamo.getId());
        return tipoReclamo;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM tipo_reclamo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

