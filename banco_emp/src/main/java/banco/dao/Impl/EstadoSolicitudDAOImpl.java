package banco.dao.Impl;

import banco.dao.EstadoSolicitudDAO;
import banco.models.EstadoSolicitud;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class EstadoSolicitudDAOImpl implements EstadoSolicitudDAO {

    private final JdbcTemplate jdbcTemplate;

    public EstadoSolicitudDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_FIELDS = "id, nombre, descripcion";
    private static final String TABLE_NAME = "estado_solicitud";

    @Override
    public List<EstadoSolicitud> findAll() {
        String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EstadoSolicitud.class));
    }

    @Override
    public Optional<EstadoSolicitud> findById(Integer id) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE id = ?";
        try {
            EstadoSolicitud es = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(EstadoSolicitud.class), id);
            return Optional.ofNullable(es);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public EstadoSolicitud save(EstadoSolicitud estadoSolicitud) {
        String sql = "INSERT INTO " + TABLE_NAME + " (nombre, descripcion) VALUES (?, ?)";
        jdbcTemplate.update(sql, estadoSolicitud.getNombre(), estadoSolicitud.getDescripcion());
        return estadoSolicitud;
    }

    @Override
    public EstadoSolicitud update(EstadoSolicitud estadoSolicitud) {
        String sql = "UPDATE " + TABLE_NAME + " SET nombre=?, descripcion=? WHERE id=?";

        jdbcTemplate.update(sql,
                estadoSolicitud.getNombre(),
                estadoSolicitud.getDescripcion(),
                estadoSolicitud.getId());
        return estadoSolicitud;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}