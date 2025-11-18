package banco.dao.Impl;

import banco.dao.ListaDocumentosDAO;
import banco.models.ListaDocumentos;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ListaDocumentosDAOImpl implements ListaDocumentosDAO {

    private final JdbcTemplate jdbcTemplate;

    public ListaDocumentosDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_FIELDS = "id, id_usuario, id_documento";

    @Override
    public List<ListaDocumentos> findAll() {
        String sql = "SELECT " + SELECT_FIELDS + " FROM lista_documentos";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ListaDocumentos.class));
    }

    @Override
    public Optional<ListaDocumentos> findById(Integer id) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM lista_documentos WHERE id = ?";
        try {
            ListaDocumentos ld = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ListaDocumentos.class), id);
            return Optional.ofNullable(ld);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public ListaDocumentos save(ListaDocumentos listaDocumentos) {
        String sql = "INSERT INTO lista_documentos (id_usuario, id_documento) VALUES (?, ?)";

        jdbcTemplate.update(sql,
                listaDocumentos.getUsuario().getId_usuario(), // Asumiendo que Usuario tiene getId_usuario()
                listaDocumentos.getDocumento().getId());
        return listaDocumentos;
    }

    @Override
    public ListaDocumentos update(ListaDocumentos listaDocumentos) {
        // En una tabla de relación simple como esta, UPDATE es raro, pero se incluye por completitud.
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
        String sql = "SELECT " + SELECT_FIELDS + " FROM lista_documentos WHERE id_usuario = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ListaDocumentos.class), idUsuario);
    }

    @Override
    public List<ListaDocumentos> findByDocumentoId(Integer idDocumento) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM lista_documentos WHERE id_documento = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ListaDocumentos.class), idDocumento);
    }
}