package banco.dao.Impl;

import banco.dao.EmpenoDAO;
import banco.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class EmpenoDAOImpl implements EmpenoDAO {

    private final JdbcTemplate jdbcTemplate;

    public EmpenoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL con JOINs para obtener todos los datos relacionados
    private static final String SELECT_WITH_JOINS =
        "SELECT e.id, e.valor_prestado, e.fecha_inicio, e.fecha_final, e.valor_recuperacion, " +
        "e.id_producto, p.nombre AS p_nombre, p.valor_solicitado AS p_valor_solicitado, " +
        "p.id_tipo, tp.nombre AS tp_nombre, tp.descripcion AS tp_descripcion, " +
        "p.id_usuario, u.nombre AS u_nombre, u.apellidos AS u_apellidos, u.dni_ruc AS u_dni, u.correo AS u_correo, " +
        "e.id_estado, es.nombre AS es_nombre, es.descripcion AS es_descripcion " +
        "FROM empeno e " +
        "LEFT JOIN producto p ON e.id_producto = p.id " +
        "LEFT JOIN tipo_producto tp ON p.id_tipo = tp.id " +
        "LEFT JOIN t_usuario u ON p.id_usuario = u.id_usuario " +
        "LEFT JOIN estado es ON e.id_estado = es.id";

    // RowMapper personalizado
    private final RowMapper<Empeno> empenoRowMapper = (rs, rowNum) -> {
        Empeno e = new Empeno();
        e.setId(rs.getInt("id"));
        e.setValorPrestado(rs.getBigDecimal("valor_prestado"));
        e.setValorRecuperacion(rs.getBigDecimal("valor_recuperacion"));
        e.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
        e.setFechaFinal(rs.getDate("fecha_final") != null ? rs.getDate("fecha_final").toLocalDate() : null);

        // Mapear Producto
        if (rs.getObject("id_producto") != null) {
            Producto p = new Producto();
            p.setId(rs.getInt("id_producto"));
            p.setNombre(rs.getString("p_nombre"));
            p.setValorSolicitado(rs.getBigDecimal("p_valor_solicitado"));

            // Mapear TipoProducto
            if (rs.getObject("id_tipo") != null) {
                TipoProducto tp = new TipoProducto();
                tp.setId(rs.getInt("id_tipo"));
                tp.setNombre(rs.getString("tp_nombre"));
                tp.setDescripcion(rs.getString("tp_descripcion"));
                p.setTipoProducto(tp);
            }

            // Mapear Usuario del Producto
            if (rs.getObject("id_usuario") != null) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("u_nombre"));
                u.setApellidos(rs.getString("u_apellidos"));
                u.setDni_ruc(rs.getString("u_dni"));
                u.setCorreo(rs.getString("u_correo"));
                p.setUsuario(u);
            }

            e.setProducto(p);
        }

        // Mapear Estado
        if (rs.getObject("id_estado") != null) {
            Estado es = new Estado();
            es.setId(rs.getInt("id_estado"));
            es.setNombre(rs.getString("es_nombre"));
            es.setDescripcion(rs.getString("es_descripcion"));
            e.setEstado(es);
        }

        return e;
    };

    @Override
    public List<Empeno> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOINS, empenoRowMapper);
    }

    @Override
    public Optional<Empeno> findById(Integer id) {
        String sql = SELECT_WITH_JOINS + " WHERE e.id = ?";
        List<Empeno> result = jdbcTemplate.query(sql, empenoRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
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
                empeno.getProducto().getId(),
                empeno.getEstado().getId());
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