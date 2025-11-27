package banco.dao.Impl;

import banco.dao.TransferenciaDAO;
import banco.models.Transferencia;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TransferenciaDAOImpl implements TransferenciaDAO {

    private final JdbcTemplate jdbcTemplate;

    public TransferenciaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_FIELDS = "id, id_usuario_emisor, id_cuenta_emisor, id_usuario_receptor, id_cuenta_receptor, id_tipo, monto, fecha";

    // ... (findAll y findById, usando BeanPropertyRowMapper) ...

    @Override
    public List<Transferencia> findAll() {
        return List.of();
    }

    @Override
    public Optional<Transferencia> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Transferencia save(Transferencia t) {
        String sql = "INSERT INTO transferencia (id_usuario_emisor, id_cuenta_emisor, id_usuario_receptor, id_cuenta_receptor, id_tipo, monto, fecha) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                t.getUsuarioEmisor().getId_usuario(), // Asumimos getId_usuario() para Usuario
                t.getCuentaEmisora().getId(),
                t.getUsuarioReceptor().getId_usuario(),
                t.getCuentaReceptora().getId(),
                t.getTipoTransferencia().getId(),
                t.getMonto(),
                t.getFecha());
        return t;
    }

    @Override
    public List<Transferencia> findByCuentaEmisoraId(Integer idCuenta) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM transferencia WHERE id_cuenta_emisor = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transferencia.class), idCuenta);
    }

    @Override
    public List<Transferencia> findByCuentaReceptoraId(Integer idCuenta) {
        String sql = "SELECT " + SELECT_FIELDS + " FROM transferencia WHERE id_cuenta_receptor = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transferencia.class), idCuenta);
    }
}