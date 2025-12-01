package banco.services.Impl;

import banco.dao.ListaEmpenosDAO;
import banco.models.ListaEmpenos;
import banco.services.ListaEmpenosService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ListaEmpenosServiceImpl implements ListaEmpenosService {

    private final ListaEmpenosDAO listaEmpenosDAO;

    public ListaEmpenosServiceImpl(ListaEmpenosDAO listaEmpenosDAO) {
        this.listaEmpenosDAO = listaEmpenosDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListaEmpenos> findAll() {
        return listaEmpenosDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListaEmpenos> findById(Integer id) {
        return listaEmpenosDAO.findById(id);
    }

    @Override
    @Transactional
    public ListaEmpenos save(ListaEmpenos listaEmpenos) {
        return listaEmpenosDAO.save(listaEmpenos);
    }

    @Override
    @Transactional
    public ListaEmpenos update(ListaEmpenos listaEmpenos) {
        return listaEmpenosDAO.update(listaEmpenos);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        listaEmpenosDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListaEmpenos> findByUsuarioId(Integer idUsuario) {
        return listaEmpenosDAO.findByUsuarioId(idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListaEmpenos> findByEmpenoId(Integer idEmpeno) {
        return listaEmpenosDAO.findByEmpenoId(idEmpeno);
    }
}

