package banco.dao.Impl;

import banco.dao.ProductoDAO;
import banco.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductoDAOImpl implements ProductoDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProductoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL con JOINs para obtener todos los datos relacionados
    private static final String SELECT_WITH_JOINS =
        "SELECT p.id, p.nombre, p.valor_solicitado, " +
        "p.id_tipo, tp.nombre AS tp_nombre, tp.descripcion AS tp_descripcion, " +
        "p.id_usuario, u.nombre AS u_nombre, u.apellidos AS u_apellidos, u.dni_ruc AS u_dni, u.correo AS u_correo " +
        "FROM producto p " +
        "LEFT JOIN tipo_producto tp ON p.id_tipo = tp.id " +
        "LEFT JOIN t_usuario u ON p.id_usuario = u.id_usuario";

    // RowMapper personalizado
    private final RowMapper<Producto> productoRowMapper = (rs, rowNum) -> {
        Producto p = new Producto();
        p.setId(rs.getInt("id"));
        p.setNombre(rs.getString("nombre"));
        p.setValorSolicitado(rs.getBigDecimal("valor_solicitado"));

        // Mapear TipoProducto
        if (rs.getObject("id_tipo") != null) {
            TipoProducto tp = new TipoProducto();
            tp.setId(rs.getInt("id_tipo"));
            tp.setNombre(rs.getString("tp_nombre"));
            tp.setDescripcion(rs.getString("tp_descripcion"));
            p.setTipoProducto(tp);
        }

        // Mapear Usuario
        if (rs.getObject("id_usuario") != null) {
            Usuario u = new Usuario();
            u.setId_usuario(rs.getInt("id_usuario"));
            u.setNombre(rs.getString("u_nombre"));
            u.setApellidos(rs.getString("u_apellidos"));
            u.setDni_ruc(rs.getString("u_dni"));
            u.setCorreo(rs.getString("u_correo"));
            p.setUsuario(u);
        }

        return p;
    };

    @Override
    public List<Producto> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOINS, productoRowMapper);
    }

    @Override
    public List<Producto> findByUsuarioId(Integer idUsuario) {
        String sql = SELECT_WITH_JOINS + " WHERE p.id_usuario = ?";
        return jdbcTemplate.query(sql, productoRowMapper, idUsuario);
    }

    @Override
    public Optional<Producto> findById(Integer id) {
        String sql = SELECT_WITH_JOINS + " WHERE p.id = ?";
        List<Producto> result = jdbcTemplate.query(sql, productoRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Producto save(Producto producto) {
        String sql = "INSERT INTO producto (nombre, valor_solicitado, id_tipo, id_usuario) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                producto.getNombre(),
                producto.getValorSolicitado(),
                producto.getTipoProducto().getId(),
                producto.getUsuario() != null ? producto.getUsuario().getId_usuario() : null);
        return producto;
    }

    @Override
    public Producto update(Producto producto) {
        String sql = "UPDATE producto SET nombre=?, valor_solicitado=?, id_tipo=?, id_usuario=? WHERE id=?";

        jdbcTemplate.update(sql,
                producto.getNombre(),
                producto.getValorSolicitado(),
                producto.getTipoProducto().getId(),
                producto.getUsuario() != null ? producto.getUsuario().getId_usuario() : null,
                producto.getId());
        return producto;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}