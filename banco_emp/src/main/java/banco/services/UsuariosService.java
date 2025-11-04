package banco.services;

import banco.models.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuariosService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Integer id);

    Usuario save(Usuario usuario);

    Usuario update(Usuario usuario);

    void deleteById(Integer id);

    // Métodos de búsqueda y validación
    Optional<Usuario> findByDniRuc(String dniRuc);
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByRol(Integer rol);

    //MétododeNegocio
    Optional<Usuario> login(String correo, String contrasena);
}