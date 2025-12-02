package banco.dao.Impl;

import banco.dao.TransferenciaDAO;
import banco.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class TransferenciaDAOImpl implements TransferenciaDAO {

    private final JdbcTemplate jdbcTemplate;

    public TransferenciaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL con JOINs para obtener todos los datos relacionados
    private static final String SELECT_WITH_JOINS =
        "SELECT t.id, t.monto, t.fecha, " +
        "t.id_usuario_emisor, ue.nombre AS ue_nombre, ue.apellidos AS ue_apellidos, ue.dni_ruc AS ue_dni, ue.correo AS ue_correo, " +
        "t.id_cuenta_emisor, ce.n_cuenta AS ce_n_cuenta, ce.n_intercuenta AS ce_n_intercuenta, ce.nombre AS ce_nombre, ce.saldo AS ce_saldo, " +
        "t.id_usuario_receptor, ur.nombre AS ur_nombre, ur.apellidos AS ur_apellidos, ur.dni_ruc AS ur_dni, ur.correo AS ur_correo, " +
        "t.id_cuenta_receptor, cr.n_cuenta AS cr_n_cuenta, cr.n_intercuenta AS cr_n_intercuenta, cr.nombre AS cr_nombre, cr.saldo AS cr_saldo, " +
        "t.id_tipo, tt.nombre AS tt_nombre, tt.descripcion AS tt_descripcion " +
        "FROM transferencia t " +
        "LEFT JOIN t_usuario ue ON t.id_usuario_emisor = ue.id_usuario " +
        "LEFT JOIN cuenta_bancaria ce ON t.id_cuenta_emisor = ce.id " +
        "LEFT JOIN t_usuario ur ON t.id_usuario_receptor = ur.id_usuario " +
        "LEFT JOIN cuenta_bancaria cr ON t.id_cuenta_receptor = cr.id " +
        "LEFT JOIN tipo_transferencia tt ON t.id_tipo = tt.id";

    // RowMapper personalizado para mapear todas las relaciones
    private final RowMapper<Transferencia> transferenciaRowMapper = new RowMapper<Transferencia>() {
        @Override
        public Transferencia mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transferencia t = new Transferencia();
            t.setId(rs.getInt("id"));
            t.setMonto(rs.getBigDecimal("monto"));
            t.setFecha(rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null);

            // Mapear Usuario Emisor
            if (rs.getObject("id_usuario_emisor") != null) {
                Usuario ue = new Usuario();
                ue.setId_usuario(rs.getInt("id_usuario_emisor"));
                ue.setNombre(rs.getString("ue_nombre"));
                ue.setApellidos(rs.getString("ue_apellidos"));
                ue.setDni_ruc(rs.getString("ue_dni"));
                ue.setCorreo(rs.getString("ue_correo"));
                t.setUsuarioEmisor(ue);
            }

            // Mapear Cuenta Emisora
            if (rs.getObject("id_cuenta_emisor") != null) {
                CuentaBancaria ce = new CuentaBancaria();
                ce.setId(rs.getInt("id_cuenta_emisor"));
                ce.setNCuenta(rs.getString("ce_n_cuenta"));
                ce.setN_intercuenta(rs.getString("ce_n_intercuenta"));
                ce.setNombre(rs.getString("ce_nombre"));
                ce.setSaldo(rs.getBigDecimal("ce_saldo"));
                t.setCuentaEmisora(ce);
            }

            // Mapear Usuario Receptor
            if (rs.getObject("id_usuario_receptor") != null) {
                Usuario ur = new Usuario();
                ur.setId_usuario(rs.getInt("id_usuario_receptor"));
                ur.setNombre(rs.getString("ur_nombre"));
                ur.setApellidos(rs.getString("ur_apellidos"));
                ur.setDni_ruc(rs.getString("ur_dni"));
                ur.setCorreo(rs.getString("ur_correo"));
                t.setUsuarioReceptor(ur);
            }

            // Mapear Cuenta Receptora
            if (rs.getObject("id_cuenta_receptor") != null) {
                CuentaBancaria cr = new CuentaBancaria();
                cr.setId(rs.getInt("id_cuenta_receptor"));
                cr.setNCuenta(rs.getString("cr_n_cuenta"));
                cr.setN_intercuenta(rs.getString("cr_n_intercuenta"));
                cr.setNombre(rs.getString("cr_nombre"));
                cr.setSaldo(rs.getBigDecimal("cr_saldo"));
                t.setCuentaReceptora(cr);
            }

            // Mapear Tipo Transferencia
            if (rs.getObject("id_tipo") != null) {
                TipoTransferencia tt = new TipoTransferencia();
                tt.setId(rs.getInt("id_tipo"));
                tt.setNombre(rs.getString("tt_nombre"));
                tt.setDescripcion(rs.getString("tt_descripcion"));
                t.setTipoTransferencia(tt);
            }

            return t;
        }
    };

    @Override
    public List<Transferencia> findAll() {
        return jdbcTemplate.query(SELECT_WITH_JOINS, transferenciaRowMapper);
    }

    @Override
    public Optional<Transferencia> findById(Integer id) {
        String sql = SELECT_WITH_JOINS + " WHERE t.id = ?";
        List<Transferencia> result = jdbcTemplate.query(sql, transferenciaRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Transferencia save(Transferencia t) {
        String sql = "INSERT INTO transferencia (id_usuario_emisor, id_cuenta_emisor, id_usuario_receptor, id_cuenta_receptor, id_tipo, monto, fecha) VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                t.getUsuarioEmisor().getId_usuario(),
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
        String sql = SELECT_WITH_JOINS + " WHERE t.id_cuenta_emisor = ?";
        return jdbcTemplate.query(sql, transferenciaRowMapper, idCuenta);
    }

    @Override
    public List<Transferencia> findByCuentaReceptoraId(Integer idCuenta) {
        String sql = SELECT_WITH_JOINS + " WHERE t.id_cuenta_receptor = ?";
        return jdbcTemplate.query(sql, transferenciaRowMapper, idCuenta);
    }
}