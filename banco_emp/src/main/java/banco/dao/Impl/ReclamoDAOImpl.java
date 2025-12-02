package banco.dao.Impl;

import banco.dao.ReclamoDAO;
import banco.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ReclamoDAOImpl implements ReclamoDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReclamoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_WITH_JOINS =
        "SELECT r.id, r.descripcion, r.fecha_reclamo, r.fecha_solucion, r.detalle_solucion, " +
        "r.id_usuario, u.nombre AS u_nombre, u.apellidos AS u_apellidos, u.dni_ruc, u.correo, " +
        "u.sexo, u.telefono, u.direccion, " +
        "r.id_tipo_reclamo, tr.nombre AS tr_nombre, tr.descripcion AS tr_descripcion, " +
        "r.id_estado, e.nombre AS e_nombre, e.descripcion AS e_descripcion " +
        "FROM reclamo r " +
        "LEFT JOIN t_usuario u ON r.id_usuario = u.id_usuario " +
        "LEFT JOIN tipo_reclamo tr ON r.id_tipo_reclamo = tr.id " +
        "LEFT JOIN estado e ON r.id_estado = e.id";

    private final RowMapper<Reclamo> reclamoRowMapper = (rs, rowNum) -> {
        Reclamo r = new Reclamo();
        r.setId(rs.getInt("id"));
        r.setDescripcion(rs.getString("descripcion"));
        r.setFechaReclamo(rs.getDate("fecha_reclamo") != null ? rs.getDate("fecha_reclamo").toLocalDate() : null);
        r.setFechaSolucion(rs.getDate("fecha_solucion") != null ? rs.getDate("fecha_solucion").toLocalDate() : null);
        r.setDetalleSolucion(rs.getString("detalle_solucion"));

        if (rs.getObject("id_usuario") != null) {
            Usuario u = new Usuario();
            u.setId_usuario(rs.getInt("id_usuario"));
            u.setNombre(rs.getString("u_nombre"));
            u.setApellidos(rs.getString("u_apellidos"));
            u.setDni_ruc(rs.getString("dni_ruc"));
            u.setCorreo(rs.getString("correo"));
            u.setSexo(rs.getString("sexo"));
            u.setTelefono(rs.getString("telefono"));
            u.setDireccion(rs.getString("direccion"));
            r.setUsuario(u);
        }

        if (rs.getObject("id_tipo_reclamo") != null) {
            TipoReclamo tr = new TipoReclamo();
            tr.setId(rs.getInt("id_tipo_reclamo"));
            tr.setNombre(rs.getString("tr_nombre"));
            tr.setDescripcion(rs.getString("tr_descripcion"));
            r.setTipoReclamo(tr);
        }

        if (rs.getObject("id_estado") != null) {
            Estado e = new Estado();
            e.setId(rs.getInt("id_estado"));
            e.setNombre(rs.getString("e_nombre"));
            e.setDescripcion(rs.getString("e_descripcion"));
            r.setEstado(e);
        }

        return r;
    };

    @Override
    public List<Reclamo> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOINS, reclamoRowMapper);
    }

    @Override
    public Optional<Reclamo> findById(Integer id) {
        String sql = SELECT_WITH_JOINS + " WHERE r.id = ?";
        List<Reclamo> result = jdbcTemplate.query(sql, reclamoRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Reclamo save(Reclamo reclamo) {
        String sql = "INSERT INTO reclamo (descripcion, fecha_reclamo, fecha_solucion, detalle_solucion, id_usuario, id_tipo_reclamo, id_estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                reclamo.getDescripcion(),
                reclamo.getFechaReclamo(),
                reclamo.getFechaSolucion(),
                reclamo.getDetalleSolucion(),
                reclamo.getUsuario() != null ? reclamo.getUsuario().getId_usuario() : null,
                reclamo.getTipoReclamo() != null ? reclamo.getTipoReclamo().getId() : null,
                reclamo.getEstado() != null ? reclamo.getEstado().getId() : null);
        return reclamo;
    }

    @Override
    public Reclamo update(Reclamo reclamo) {
        String sql = "UPDATE reclamo SET descripcion=?, fecha_reclamo=?, fecha_solucion=?, detalle_solucion=?, id_usuario=?, id_tipo_reclamo=?, id_estado=? WHERE id=?";
        jdbcTemplate.update(sql,
                reclamo.getDescripcion(),
                reclamo.getFechaReclamo(),
                reclamo.getFechaSolucion(),
                reclamo.getDetalleSolucion(),
                reclamo.getUsuario() != null ? reclamo.getUsuario().getId_usuario() : null,
                reclamo.getTipoReclamo() != null ? reclamo.getTipoReclamo().getId() : null,
                reclamo.getEstado() != null ? reclamo.getEstado().getId() : null,
                reclamo.getId());
        return reclamo;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM reclamo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Reclamo> findByUsuarioId(Integer idUsuario) {
        String sql = SELECT_WITH_JOINS + " WHERE r.id_usuario = ?";
        return jdbcTemplate.query(sql, reclamoRowMapper, idUsuario);
    }
}

