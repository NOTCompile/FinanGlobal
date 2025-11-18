package banco.dao.Impl;

import banco.dao.EstadoDAO;
import banco.models.Estado;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class EstadoDAOImpl implements EstadoDAO {

    private final JdbcTemplate jdbcTemplate;

    public EstadoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_FIELDS = "id, nombre, descripcion";

    @Override
    public List<Estado> findAll() {
        String sql = "SELECT " + SELECT_FIELDS + " FROM estado";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Estado.class));
    }

    @Override
    public Optional<Estado> findById(Integer id) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM estado WHERE id = ?";
        try {
            Estado e = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Estado.class), id);
            return Optional.ofNullable(e);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Estado save(Estado estado) {
        String sql = "INSERT INTO estado (nombre, descripcion) VALUES (?, ?)";
        jdbcTemplate.update(sql, estado.getNombre(), estado.getDescripcion());
        return estado;
    }

    @Override
    public Estado update(Estado estado) {
        String sql = "UPDATE estado SET nombre=?, descripcion=? WHERE id=?";

        jdbcTemplate.update(sql,
                estado.getNombre(),
                estado.getDescripcion(),
                estado.getId());
        return estado;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM estado WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}