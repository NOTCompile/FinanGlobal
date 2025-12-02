package banco.dao;

import banco.models.ListaCuentasBancarias;
import java.util.List;
import java.util.Optional;

public interface ListaCuentasBancariasDAO {
    List<ListaCuentasBancarias> findAll();
    Optional<ListaCuentasBancarias> findById(Integer id);
    ListaCuentasBancarias save(ListaCuentasBancarias listaCuentasBancarias);
    ListaCuentasBancarias update(ListaCuentasBancarias listaCuentasBancarias);
    void deleteById(Integer id);
}