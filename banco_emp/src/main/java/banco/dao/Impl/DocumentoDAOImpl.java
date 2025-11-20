package banco.dao.Impl;

import banco.dao.DocumentoDAO;
import banco.models.Documento;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class DocumentoDAOImpl implements DocumentoDAO {

    private final JdbcTemplate jdbcTemplate;

    public DocumentoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Usamos 'path' porque es el nombre de la columna en la base de datos
    private static final String SELECT_FIELDS = "id, nombre, fecha, path, id_tipo_documento";

    @Override
    public List<Documento> findAll() {
        String sql = "SELECT " + SELECT_FIELDS + " FROM documento";
        // Nota: Para que BeanPropertyRowMapper funcione, los getters/setters de Documento
        // deben coincidir con los nombres de las columnas (id, nombre, fecha, rutaArchivo, idTipoDocumento).
        // En este caso, 'path' en DB mapea a 'rutaArchivo' en Java, y 'id_tipo_documento' a 'tipoDocumento' (por convención JPA/Hibernate),
        // pero para JDBC simple, es mejor mapear directamente a las columnas de la DB.
        // Si tienes problemas de mapeo con la columna 'path' o 'id_tipo_documento', considera usar un RowMapper personalizado.
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Documento.class));
    }

    @Override
    public Optional<Documento> findById(Integer id) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM documento WHERE id = ?";
        try {
            Documento d = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Documento.class), id);
            return Optional.ofNullable(d);
        } catch (Exception e) {
            return Optional.empty();
        }
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