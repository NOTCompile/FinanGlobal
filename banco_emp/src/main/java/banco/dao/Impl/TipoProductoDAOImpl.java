package banco.dao.Impl;

import banco.dao.TipoProductoDAO;
import banco.models.TipoProducto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TipoProductoDAOImpl implements TipoProductoDAO {

    private final JdbcTemplate jdbcTemplate;

    public TipoProductoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_FIELDS = "id, nombre, descripcion";

    @Override
    public List<TipoProducto> findAll() {
        String sql = "SELECT " + SELECT_FIELDS + " FROM tipo_producto";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TipoProducto.class));
    }

    @Override
    public Optional<TipoProducto> findById(Integer id) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM tipo_producto WHERE id = ?";
        try {
            TipoProducto tp = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TipoProducto.class), id);
            return Optional.ofNullable(tp);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public TipoProducto save(TipoProducto tipoProducto) {
        String sql = "INSERT INTO tipo_producto (nombre, descripcion) VALUES (?, ?)";
        jdbcTemplate.update(sql, tipoProducto.getNombre(), tipoProducto.getDescripcion());
        return tipoProducto;
    }

    @Override
    public TipoProducto update(TipoProducto tipoProducto) {
        String sql = "UPDATE tipo_producto SET nombre=?, descripcion=? WHERE id=?";

        jdbcTemplate.update(sql,
                tipoProducto.getNombre(),
                tipoProducto.getDescripcion(),
                tipoProducto.getId());
        return tipoProducto;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM tipo_producto WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}