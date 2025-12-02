package banco.dao.Impl;

import banco.dao.TipoTransferenciaDAO;
import banco.models.TipoTransferencia;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TipoTransferenciaDAOImpl implements TipoTransferenciaDAO {

    private final JdbcTemplate jdbcTemplate;

    public TipoTransferenciaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_FIELDS = "id, nombre, descripcion";

    @Override
    public List<TipoTransferencia> findAll() {
        String sql = "SELECT " + SELECT_FIELDS + " FROM tipo_transferencia";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TipoTransferencia.class));
    }

    @Override
    public Optional<TipoTransferencia> findById(Integer id) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM tipo_transferencia WHERE id = ?";
        try {
            TipoTransferencia tt = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TipoTransferencia.class), id);
            return Optional.ofNullable(tt);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public TipoTransferencia save(TipoTransferencia tipoTransferencia) {
        String sql = "INSERT INTO tipo_transferencia (nombre, descripcion) VALUES (?, ?)";
        jdbcTemplate.update(sql, tipoTransferencia.getNombre(), tipoTransferencia.getDescripcion());
        return tipoTransferencia;
    }

    @Override
    public TipoTransferencia update(TipoTransferencia tipoTransferencia) {
        String sql = "UPDATE tipo_transferencia SET nombre=?, descripcion=? WHERE id=?";

        jdbcTemplate.update(sql,
                tipoTransferencia.getNombre(),
                tipoTransferencia.getDescripcion(),
                tipoTransferencia.getId());
        return tipoTransferencia;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM tipo_transferencia WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}