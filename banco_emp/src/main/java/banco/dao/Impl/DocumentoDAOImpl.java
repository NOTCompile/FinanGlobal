package banco.dao.Impl;

import banco.dao.DocumentoDAO;
import banco.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DocumentoDAOImpl implements DocumentoDAO {

    private final JdbcTemplate jdbcTemplate;

    public DocumentoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL con JOINs para obtener todos los datos relacionados
    private static final String SELECT_WITH_JOINS =
        "SELECT d.id, d.nombre, d.fecha, d.path, " +
        "d.id_tipo_documento, td.tipo AS td_tipo, td.descripcion AS td_descripcion " +
        "FROM documento d " +
        "LEFT JOIN tipo_documento td ON d.id_tipo_documento = td.id";

    // RowMapper personalizado
    private final RowMapper<Documento> documentoRowMapper = (rs, rowNum) -> {
        Documento d = new Documento();
        d.setId(rs.getInt("id"));
        d.setNombre(rs.getString("nombre"));
        d.setFecha(rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null);
        d.setRutaArchivo(rs.getString("path"));

        // Mapear TipoDocumento
        if (rs.getObject("id_tipo_documento") != null) {
            TipoDocumento td = new TipoDocumento();
            td.setId(rs.getInt("id_tipo_documento"));
            td.setTipo(rs.getString("td_tipo"));
            td.setDescripcion(rs.getString("td_descripcion"));
            d.setTipoDocumento(td);
        }

        return d;
    };

    @Override
    public List<Documento> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOINS, documentoRowMapper);
    }

    @Override
    public Optional<Documento> findById(Integer id) {
        String sql = SELECT_WITH_JOINS + " WHERE d.id = ?";
        List<Documento> result = jdbcTemplate.query(sql, documentoRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Documento save(Documento documento) {
        String sql = "INSERT INTO documento (nombre, fecha, path, id_tipo_documento) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                documento.getNombre(),
                documento.getFecha(),
                documento.getRutaArchivo(),
                documento.getTipoDocumento().getId());
        return documento;
    }

    @Override
    public Documento update(Documento documento) {
        String sql = "UPDATE documento SET nombre=?, fecha=?, path=?, id_tipo_documento=? WHERE id=?";

        jdbcTemplate.update(sql,
                documento.getNombre(),
                documento.getFecha(),
                documento.getRutaArchivo(),
                documento.getTipoDocumento().getId(),
                documento.getId());
        return documento;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM documento WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}