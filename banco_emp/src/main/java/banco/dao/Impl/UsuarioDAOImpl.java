package banco.dao.Impl;

import banco.dao.UsuarioDAO;
import banco.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    private JdbcTemplate jdbcTemplate;

    public UsuarioDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ===== CRUD =====

    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM t_usuario";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Usuario.class));
    }

    @Override
    public Optional<Usuario> findById(Integer id_usuario) {
        String sql = "SELECT * FROM t_usuario WHERE id_usuario = ?";
        try {
            Usuario usuario = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Usuario.class), id_usuario);
            return Optional.ofNullable(usuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Usuario save(Usuario usuario) {
        String sql = "INSERT INTO t_usuario (nombre, apellidos, dni_ruc, sexo, correo, telefono, direccion, nombre_usuario, contrasena, rol_usuario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getDni_ruc(),
                usuario.getSexo(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getNombre_usuario(),
                usuario.getContrasena(),
                usuario.getRol_usuario());
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {
        String sql = "UPDATE t_usuario SET nombre=?, apellidos=?, dni_ruc=?, sexo=?, correo=?, telefono=?, direccion=?, nombre_usuario=?, contrasena=?, rol_usuario=? " +
                "WHERE id_usuario=?";
        jdbcTemplate.update(sql,
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getDni_ruc(),
                usuario.getSexo(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getNombre_usuario(),
                usuario.getContrasena(),
                usuario.getRol_usuario(),
                usuario.getId_usuario());
        return usuario;
    }

    @Override
    public void deleteById(Integer id_usuario) {
        String sql = "DELETE FROM t_usuario WHERE id_usuario = ?";
        jdbcTemplate.update(sql, id_usuario);
    }

    // ===== BÚSQUEDAS =====

    @Override
    public Optional<Usuario> findByDniRuc(String dniRuc) {
        String sql = "SELECT * FROM t_usuario WHERE dni_ruc = ?";
        try {
            Usuario usuario = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Usuario.class), dniRuc);
            return Optional.ofNullable(usuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        String sql = "SELECT * FROM t_usuario WHERE correo = ?";
        try {
            Usuario usuario = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Usuario.class), correo);
            return Optional.ofNullable(usuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Usuario> findByRol(Integer rolUsuario) {
        String sql = "SELECT * FROM t_usuario WHERE rol_usuario = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Usuario.class), rolUsuario);
    }

    @Override
    public List<Usuario> findBySexo(String sexo) {
        String sql = "SELECT * FROM t_usuario WHERE sexo = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Usuario.class), sexo);
    }

    // ===== VERIFICACIONES =====

    @Override
    public boolean existsByDniRuc(String dniRuc) {
        String sql = "SELECT COUNT(*) FROM t_usuario WHERE dni_ruc = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, dniRuc);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM t_usuario WHERE correo = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, correo);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByNombreUsuario(String nombreUsuario) {
        String sql = "SELECT COUNT(*) FROM t_usuario WHERE nombre_usuario = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nombreUsuario);
        return count != null && count > 0;
    }
}
