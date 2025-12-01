package banco.dao.Impl;

import banco.dao.ListaEmpenosDAO;
import banco.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ListaEmpenosDAOImpl implements ListaEmpenosDAO {

    private final JdbcTemplate jdbcTemplate;

    public ListaEmpenosDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL con JOINs para obtener todos los datos relacionados
    private static final String SELECT_WITH_JOINS =
        "SELECT le.id, " +
        "le.id_usuario, u.nombre AS u_nombre, u.apellidos AS u_apellidos, u.dni_ruc, u.correo, " +
        "u.sexo, u.telefono, u.direccion, " +
        "le.id_empeno, e.valor_prestado, e.valor_recuperacion, e.fecha_inicio, e.fecha_final, " +
        "e.id_producto, p.nombre AS p_nombre, p.valor_solicitado, " +
        "p.id_tipo, tp.nombre AS tp_nombre, tp.descripcion AS tp_descripcion, " +
        "e.id_estado, es.nombre AS es_nombre, es.descripcion AS es_descripcion " +
        "FROM lista_empenos le " +
        "LEFT JOIN t_usuario u ON le.id_usuario = u.id_usuario " +
        "LEFT JOIN empeno e ON le.id_empeno = e.id " +
        "LEFT JOIN producto p ON e.id_producto = p.id " +
        "LEFT JOIN tipo_producto tp ON p.id_tipo = tp.id " +
        "LEFT JOIN estado es ON e.id_estado = es.id";

    // RowMapper personalizado
    private final RowMapper<ListaEmpenos> listaEmpenosRowMapper = (rs, rowNum) -> {
        ListaEmpenos le = new ListaEmpenos();
        le.setId(rs.getInt("id"));

        // Mapear Usuario
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
            le.setUsuario(u);
        }

        // Mapear Empeno
        if (rs.getObject("id_empeno") != null) {
            Empeno e = new Empeno();
            e.setId(rs.getInt("id_empeno"));
            e.setValorPrestado(rs.getBigDecimal("valor_prestado"));
            e.setValorRecuperacion(rs.getBigDecimal("valor_recuperacion"));
            e.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
            e.setFechaFinal(rs.getDate("fecha_final") != null ? rs.getDate("fecha_final").toLocalDate() : null);

            // Mapear Producto del Empeno
            if (rs.getObject("id_producto") != null) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("p_nombre"));
                p.setValorSolicitado(rs.getBigDecimal("valor_solicitado"));

                // Mapear TipoProducto
                if (rs.getObject("id_tipo") != null) {
                    TipoProducto tp = new TipoProducto();
                    tp.setId(rs.getInt("id_tipo"));
                    tp.setNombre(rs.getString("tp_nombre"));
                    tp.setDescripcion(rs.getString("tp_descripcion"));
                    p.setTipoProducto(tp);
                }

                e.setProducto(p);
            }

            // Mapear Estado del Empeno
            if (rs.getObject("id_estado") != null) {
                Estado es = new Estado();
                es.setId(rs.getInt("id_estado"));
                es.setNombre(rs.getString("es_nombre"));
                es.setDescripcion(rs.getString("es_descripcion"));
                e.setEstado(es);
            }

            le.setEmpeno(e);
        }

        return le;
    };

    @Override
    public List<ListaEmpenos> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOINS, listaEmpenosRowMapper);
    }

    @Override
    public Optional<ListaEmpenos> findById(Integer id) {
        String sql = SELECT_WITH_JOINS + " WHERE le.id = ?";
        List<ListaEmpenos> result = jdbcTemplate.query(sql, listaEmpenosRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public ListaEmpenos save(ListaEmpenos listaEmpenos) {
        String sql = "INSERT INTO lista_empenos (id_usuario, id_empeno) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                listaEmpenos.getUsuario().getId_usuario(),
                listaEmpenos.getEmpeno().getId());
        return listaEmpenos;
    }

    @Override
    public ListaEmpenos update(ListaEmpenos listaEmpenos) {
        String sql = "UPDATE lista_empenos SET id_usuario=?, id_empeno=? WHERE id=?";
        jdbcTemplate.update(sql,
                listaEmpenos.getUsuario().getId_usuario(),
                listaEmpenos.getEmpeno().getId(),
                listaEmpenos.getId());
        return listaEmpenos;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM lista_empenos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ListaEmpenos> findByUsuarioId(Integer idUsuario) {
        String sql = SELECT_WITH_JOINS + " WHERE le.id_usuario = ?";
        return jdbcTemplate.query(sql, listaEmpenosRowMapper, idUsuario);
    }

    @Override
    public List<ListaEmpenos> findByEmpenoId(Integer idEmpeno) {
        String sql = SELECT_WITH_JOINS + " WHERE le.id_empeno = ?";
        return jdbcTemplate.query(sql, listaEmpenosRowMapper, idEmpeno);
    }
}

