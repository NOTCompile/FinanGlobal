package banco.dao.Impl;

import banco.dao.ProductoDAO;
import banco.models.Producto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductoDAOImpl implements ProductoDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProductoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_FIELDS = "id, nombre, valor_solicitado, id_tipo";

    @Override
    public List<Producto> findAll() {
        String sql = "SELECT " + SELECT_FIELDS + " FROM producto";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Producto.class));
    }

    @Override
    public Optional<Producto> findById(Integer id) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM producto WHERE id = ?";
        try {
            Producto p = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Producto.class), id);
            return Optional.ofNullable(p);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Producto save(Producto producto) {
        String sql = "INSERT INTO producto (nombre, valor_solicitado, id_tipo) VALUES (?, ?, ?)";

        // Asumo que tienes el objeto TipoProducto completo en la entidad Producto.
        jdbcTemplate.update(sql,
                producto.getNombre(),
                producto.getValorSolicitado(),
                producto.getTipoProducto().getId()); // Asumo que TipoProducto tiene un getId()
        return producto;
    }

    @Override
    public Producto update(Producto producto) {
        String sql = "UPDATE producto SET nombre=?, valor_solicitado=?, id_tipo=? WHERE id=?";

        jdbcTemplate.update(sql,
                producto.getNombre(),
                producto.getValorSolicitado(),
                producto.getTipoProducto().getId(),
                producto.getId());
        return producto;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}