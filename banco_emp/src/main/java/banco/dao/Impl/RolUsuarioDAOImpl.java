package banco.dao.Impl;

import banco.dao.RolUsuarioDAO;
import banco.models.RolUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class RolUsuarioDAOImpl implements RolUsuarioDAO {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RolUsuario> findAll() {
        String sql = "SELECT * FROM rol_usuario";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RolUsuario.class));
    }

    @Override
    public Optional<RolUsuario> findById(Integer id) {
        String sql = "SELECT * FROM rol_usuario WHERE id = ?";
        try {
            RolUsuario r = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(RolUsuario.class), id);
            return Optional.ofNullable(r);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public RolUsuario save(RolUsuario rol) {
        String sql = "INSERT INTO rol_usuario (rol, descripcion) VALUES (?, ?)";
        jdbcTemplate.update(sql, rol.getRol(), rol.getDescripcion());
        return rol;
    }

    @Override
    public RolUsuario update(RolUsuario rol) {
        String sql = "UPDATE rol_usuario SET rol=?, descripcion=? WHERE id=?";
        jdbcTemplate.update(sql, rol.getRol(), rol.getDescripcion(), rol.getId());
        return rol;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM rol_usuario WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}