package banco.dao.Impl;

import banco.dao.TipoDocumentoDAO;
import banco.models.TipoDocumento;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TipoDocumentoDAOImpl implements TipoDocumentoDAO {

    private final JdbcTemplate jdbcTemplate;

    public TipoDocumentoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_FIELDS = "id, tipo, descripcion";

    @Override
    public List<TipoDocumento> findAll() {
        String sql = "SELECT " + SELECT_FIELDS + " FROM tipo_documento";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TipoDocumento.class));
    }

    @Override
    public Optional<TipoDocumento> findById(Integer id) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM tipo_documento WHERE id = ?";
        try {
            TipoDocumento td = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TipoDocumento.class), id);
            return Optional.ofNullable(td);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public TipoDocumento save(TipoDocumento tipoDocumento) {
        String sql = "INSERT INTO tipo_documento (tipo, descripcion) VALUES (?, ?)";
        jdbcTemplate.update(sql, tipoDocumento.getTipo(), tipoDocumento.getDescripcion());
        return tipoDocumento;
    }

    @Override
    public TipoDocumento update(TipoDocumento tipoDocumento) {
        String sql = "UPDATE tipo_documento SET tipo=?, descripcion=? WHERE id=?";

        jdbcTemplate.update(sql,
                tipoDocumento.getTipo(),
                tipoDocumento.getDescripcion(),
                tipoDocumento.getId());
        return tipoDocumento;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM tipo_documento WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}