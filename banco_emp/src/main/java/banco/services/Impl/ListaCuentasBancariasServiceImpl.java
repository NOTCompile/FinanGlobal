package banco.services.Impl;

import banco.dao.ListaCuentasBancariasDAO;
import banco.models.ListaCuentasBancarias;
import banco.services.ListaCuentasBancariasService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ListaCuentasBancariasServiceImpl implements ListaCuentasBancariasService {

    private final ListaCuentasBancariasDAO listaCuentasBancariasDAO;

    public ListaCuentasBancariasServiceImpl(ListaCuentasBancariasDAO listaCuentasBancariasDAO) {
        this.listaCuentasBancariasDAO = listaCuentasBancariasDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListaCuentasBancarias> findAll() {
        return listaCuentasBancariasDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListaCuentasBancarias> findById(Integer id) {
        return listaCuentasBancariasDAO.findById(id);
    }

    @Override
    @Transactional
    public ListaCuentasBancarias save(ListaCuentasBancarias listaCuentasBancarias) {
        // Lógica de negocio antes de guardar
        return listaCuentasBancariasDAO.save(listaCuentasBancarias);
    }

    @Override
    @Transactional
    public ListaCuentasBancarias update(ListaCuentasBancarias listaCuentasBancarias) {
        if (listaCuentasBancariasDAO.findById(listaCuentasBancarias.getId()).isEmpty()) {
            throw new RuntimeException("Registro ListaCuentasBancarias no encontrado para actualizar.");
        }
        return listaCuentasBancariasDAO.update(listaCuentasBancarias);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        listaCuentasBancariasDAO.deleteById(id);
    }
}