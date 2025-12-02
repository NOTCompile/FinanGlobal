package banco.dao.Impl;

import banco.dao.TipoSolicitudDAO;
import banco.models.TipoSolicitud;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TipoSolicitudDAOImpl implements TipoSolicitudDAO {

    private final JdbcTemplate jdbcTemplate;

    public TipoSolicitudDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_ALL = "SELECT id, nombre, descripcion FROM tipo_solicitud";

    private final RowMapper<TipoSolicitud> tipoSolicitudRowMapper = (rs, rowNum) -> {
        TipoSolicitud ts = new TipoSolicitud();
        ts.setId(rs.getInt("id"));
        ts.setNombre(rs.getString("nombre"));
        ts.setDescripcion(rs.getString("descripcion"));
        return ts;
    };

    @Override
    public List<TipoSolicitud> findAll() {
        return jdbcTemplate.query(SELECT_ALL, tipoSolicitudRowMapper);
    }

    @Override
    public Optional<TipoSolicitud> findById(Integer id) {
        String sql = SELECT_ALL + " WHERE id = ?";
        List<TipoSolicitud> result = jdbcTemplate.query(sql, tipoSolicitudRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public TipoSolicitud save(TipoSolicitud tipoSolicitud) {
        String sql = "INSERT INTO tipo_solicitud (nombre, descripcion) VALUES (?, ?)";
        jdbcTemplate.update(sql, tipoSolicitud.getNombre(), tipoSolicitud.getDescripcion());
        return tipoSolicitud;
    }

    @Override
    public TipoSolicitud update(TipoSolicitud tipoSolicitud) {
        String sql = "UPDATE tipo_solicitud SET nombre=?, descripcion=? WHERE id=?";
        jdbcTemplate.update(sql, tipoSolicitud.getNombre(), tipoSolicitud.getDescripcion(), tipoSolicitud.getId());
        return tipoSolicitud;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM tipo_solicitud WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

