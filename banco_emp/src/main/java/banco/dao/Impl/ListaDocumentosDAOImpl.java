package banco.dao.Impl;

import banco.dao.ListaDocumentosDAO;
import banco.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ListaDocumentosDAOImpl implements ListaDocumentosDAO {

    private final JdbcTemplate jdbcTemplate;

    public ListaDocumentosDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL con JOINs para obtener todos los datos relacionados
    private static final String SELECT_WITH_JOINS =
        "SELECT ld.id, " +
        "ld.id_usuario, u.nombre AS u_nombre, u.apellidos AS u_apellidos, u.dni_ruc, u.correo, " +
        "ld.id_documento, d.nombre AS d_nombre, d.fecha, d.path, " +
        "d.id_tipo_documento, td.tipo, td.descripcion AS td_descripcion " +
        "FROM lista_documentos ld " +
        "LEFT JOIN t_usuario u ON ld.id_usuario = u.id_usuario " +
        "LEFT JOIN documento d ON ld.id_documento = d.id " +
        "LEFT JOIN tipo_documento td ON d.id_tipo_documento = td.id";

    // RowMapper personalizado
    private final RowMapper<ListaDocumentos> listaDocumentosRowMapper = (rs, rowNum) -> {
        ListaDocumentos ld = new ListaDocumentos();
        ld.setId(rs.getInt("id"));

        // Mapear Usuario
        if (rs.getObject("id_usuario") != null) {
            Usuario u = new Usuario();
            u.setId_usuario(rs.getInt("id_usuario"));
            u.setNombre(rs.getString("u_nombre"));
            u.setApellidos(rs.getString("u_apellidos"));
            u.setDni_ruc(rs.getString("dni_ruc"));
            u.setCorreo(rs.getString("correo"));
            ld.setUsuario(u);
        }

        // Mapear Documento
        if (rs.getObject("id_documento") != null) {
            Documento d = new Documento();
            d.setId(rs.getInt("id_documento"));
            d.setNombre(rs.getString("d_nombre"));
            d.setFecha(rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null);
            d.setRutaArchivo(rs.getString("path"));

            // Mapear TipoDocumento
            if (rs.getObject("id_tipo_documento") != null) {
                TipoDocumento td = new TipoDocumento();
                td.setId(rs.getInt("id_tipo_documento"));
                td.setTipo(rs.getString("tipo"));
                td.setDescripcion(rs.getString("td_descripcion"));
                d.setTipoDocumento(td);
            }

            ld.setDocumento(d);
        }

        return ld;
    };

    @Override
    public List<ListaDocumentos> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOINS, listaDocumentosRowMapper);
    }

    @Override
    public Optional<ListaDocumentos> findById(Integer id) {
        String sql = SELECT_WITH_JOINS + " WHERE ld.id = ?";
        List<ListaDocumentos> result = jdbcTemplate.query(sql, listaDocumentosRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public ListaDocumentos save(ListaDocumentos listaDocumentos) {
        String sql = "INSERT INTO lista_documentos (id_usuario, id_documento) VALUES (?, ?)";

        jdbcTemplate.update(sql,
                listaDocumentos.getUsuario().getId_usuario(),
                listaDocumentos.getDocumento().getId());
        return listaDocumentos;
    }

    @Override
    public ListaDocumentos update(ListaDocumentos listaDocumentos) {
        String sql = "UPDATE lista_documentos SET id_usuario=?, id_documento=? WHERE id=?";

        jdbcTemplate.update(sql,
                listaDocumentos.getUsuario().getId_usuario(),
                listaDocumentos.getDocumento().getId(),
                listaDocumentos.getId());
        return listaDocumentos;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM lista_documentos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ListaDocumentos> findByUsuarioId(Integer idUsuario) {
        String sql = SELECT_WITH_JOINS + " WHERE ld.id_usuario = ?";
        return jdbcTemplate.query(sql, listaDocumentosRowMapper, idUsuario);
    }

    @Override
    public List<ListaDocumentos> findByDocumentoId(Integer idDocumento) {
        String sql = SELECT_WITH_JOINS + " WHERE ld.id_documento = ?";
        return jdbcTemplate.query(sql, listaDocumentosRowMapper, idDocumento);
    }
}