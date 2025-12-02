package banco.dao;

import banco.models.ListaEmpenos;
import java.util.List;
import java.util.Optional;

public interface ListaEmpenosDAO {
    List<ListaEmpenos> findAll();
    Optional<ListaEmpenos> findById(Integer id);
    ListaEmpenos save(ListaEmpenos listaEmpenos);
    ListaEmpenos update(ListaEmpenos listaEmpenos);
    void deleteById(Integer id);
    List<ListaEmpenos> findByUsuarioId(Integer idUsuario);
    List<ListaEmpenos> findByEmpenoId(Integer idEmpeno);
}

