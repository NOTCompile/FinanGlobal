package banco.dao.Impl;

import banco.dao.SolicitudDAO;
import banco.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class SolicitudDAOImpl implements SolicitudDAO {

    private final JdbcTemplate jdbcTemplate;

    public SolicitudDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_WITH_JOINS =
        "SELECT s.id, s.descripcion, s.fecha_inicio, s.fecha_fin, " +
        "s.id_tipo, ts.nombre AS ts_nombre, ts.descripcion AS ts_descripcion, " +
        "s.id_estado, es.nombre AS es_nombre, es.descripcion AS es_descripcion, " +
        "s.id_producto, p.nombre AS p_nombre, p.valor_solicitado " +
        "FROM solicitud s " +
        "LEFT JOIN tipo_solicitud ts ON s.id_tipo = ts.id " +
        "LEFT JOIN estado_solicitud es ON s.id_estado = es.id " +
        "LEFT JOIN producto p ON s.id_producto = p.id";

    private final RowMapper<Solicitud> solicitudRowMapper = (rs, rowNum) -> {
        Solicitud s = new Solicitud();
        s.setId(rs.getInt("id"));
        s.setDescripcion(rs.getString("descripcion"));
        s.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
        s.setFechaFin(rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null);

        if (rs.getObject("id_tipo") != null) {
            TipoSolicitud ts = new TipoSolicitud();
            ts.setId(rs.getInt("id_tipo"));
            ts.setNombre(rs.getString("ts_nombre"));
            ts.setDescripcion(rs.getString("ts_descripcion"));
            s.setTipoSolicitud(ts);
        }

        if (rs.getObject("id_estado") != null) {
            EstadoSolicitud es = new EstadoSolicitud();
            es.setId(rs.getInt("id_estado"));
            es.setNombre(rs.getString("es_nombre"));
            es.setDescripcion(rs.getString("es_descripcion"));
            s.setEstadoSolicitud(es);
        }

        if (rs.getObject("id_producto") != null) {
            Producto p = new Producto();
            p.setId(rs.getInt("id_producto"));
            p.setNombre(rs.getString("p_nombre"));
            p.setValorSolicitado(rs.getBigDecimal("valor_solicitado"));
            s.setProducto(p);
        }

        return s;
    };

    @Override
    public List<Solicitud> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOINS, solicitudRowMapper);
    }

    @Override
    public Optional<Solicitud> findById(Integer id) {
        String sql = SELECT_WITH_JOINS + " WHERE s.id = ?";
        List<Solicitud> result = jdbcTemplate.query(sql, solicitudRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        String sql = "INSERT INTO solicitud (descripcion, fecha_inicio, fecha_fin, id_tipo, id_estado, id_producto) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                solicitud.getDescripcion(),
                solicitud.getFechaInicio(),
                solicitud.getFechaFin(),
                solicitud.getTipoSolicitud() != null ? solicitud.getTipoSolicitud().getId() : null,
                solicitud.getEstadoSolicitud() != null ? solicitud.getEstadoSolicitud().getId() : null,
                solicitud.getProducto() != null ? solicitud.getProducto().getId() : null);
        return solicitud;
    }

    @Override
    public Solicitud update(Solicitud solicitud) {
        String sql = "UPDATE solicitud SET descripcion=?, fecha_inicio=?, fecha_fin=?, id_tipo=?, id_estado=?, id_producto=? WHERE id=?";
        jdbcTemplate.update(sql,
                solicitud.getDescripcion(),
                solicitud.getFechaInicio(),
                solicitud.getFechaFin(),
                solicitud.getTipoSolicitud() != null ? solicitud.getTipoSolicitud().getId() : null,
                solicitud.getEstadoSolicitud() != null ? solicitud.getEstadoSolicitud().getId() : null,
                solicitud.getProducto() != null ? solicitud.getProducto().getId() : null,
                solicitud.getId());
        return solicitud;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM solicitud WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Solicitud> findByProductoId(Integer idProducto) {
        String sql = SELECT_WITH_JOINS + " WHERE s.id_producto = ?";
        return jdbcTemplate.query(sql, solicitudRowMapper, idProducto);
    }
}

