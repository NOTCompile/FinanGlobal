package banco.services.Impl;


import banco.dao.UsuarioDAO;
import banco.models.Usuario;
import banco.services.UsuariosService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImpl implements UsuariosService {

    private final UsuarioDAO usuarioDAO;

    public UsuariosServiceImpl(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Integer id) {
        return usuarioDAO.findById(id);
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        // Lógica de validación movida aquí:
        if (usuarioDAO.existsByDniRuc(usuario.getDni_ruc())) {
            throw new IllegalArgumentException("El DNI/RUC ya está registrado.");
        }
        if (usuarioDAO.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }

        // Aquí se podría encriptar la contraseña antes de guardar.
        return usuarioDAO.save(usuario);
    }

    @Override
    @Transactional
    public Usuario update(Usuario usuario) {
        // Se asume que la validación de existencia se hará en el controlador o se manejará aquí.
        return usuarioDAO.update(usuario);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        usuarioDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByDniRuc(String dniRuc) {
        return usuarioDAO.findByDniRuc(dniRuc);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioDAO.findByCorreo(correo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByRol(Integer rol) {
        return usuarioDAO.findByRol(rol);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> login(String correo, String contrasena) {
        Optional<Usuario> usuarioOpt = usuarioDAO.findByCorreo(correo);

        if (usuarioOpt.isEmpty()) {
            // No lanzamos una excepción para que el controlador pueda devolver 401 más limpio.
            return Optional.empty();
        }

        Usuario usuario = usuarioOpt.get();
        // Lógica de validación de contraseña (¡idealmente usando BCrypt!)
        if (!usuario.getContrasena().equals(contrasena)) {
            // Si la contraseña es incorrecta, devolvemos Optional.empty()
            return Optional.empty();
        }

        return usuarioOpt;
    }
}