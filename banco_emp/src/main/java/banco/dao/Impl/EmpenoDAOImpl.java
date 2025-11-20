package banco.dao.Impl;

import banco.dao.EmpenoDAO;
import banco.models.Empeno;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class EmpenoDAOImpl implements EmpenoDAO {

    private final JdbcTemplate jdbcTemplate;

    public EmpenoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_FIELDS =
            "id, valor_prestado, valor_recuperacion, fecha_inicio, fecha_final, id_producto, id_estado";

    @Override
    public List<Empeno> findAll() {
        String sql = "SELECT " + SELECT_FIELDS + " FROM empeno";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Empeno.class));
    }

    @Override
    public Optional<Empeno> findById(Integer id) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM empeno WHERE id = ?";
        try {
            Empeno e = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Empeno.class), id);
            return Optional.ofNullable(e);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Empeno save(Empeno empeno) {
        String sql = "INSERT INTO empeno (valor_prestado, valor_recuperacion, fecha_inicio, fecha_final, id_producto, id_estado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                empeno.getValorPrestado(),
                empeno.getValorRecuperacion(),
                empeno.getFechaInicio(),
                empeno.getFechaFinal(),
                empeno.getProducto().getId(), // Asumiendo que Producto tiene un getId()
                empeno.getEstado().getId());   // Asumiendo que Estado tiene un getId()
        return empeno;
    }

    @Override
    public Empeno update(Empeno empeno) {
        String sql = "UPDATE empeno SET valor_prestado=?, valor_recuperacion=?, fecha_inicio=?, fecha_final=?, id_producto=?, id_estado=? WHERE id=?";

        jdbcTemplate.update(sql,
                empeno.getValorPrestado(),
                empeno.getValorRecuperacion(),
                empeno.getFechaInicio(),
                empeno.getFechaFinal(),
                empeno.getProducto().getId(),
                empeno.getEstado().getId(),
                empeno.getId());
        return empeno;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM empeno WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}