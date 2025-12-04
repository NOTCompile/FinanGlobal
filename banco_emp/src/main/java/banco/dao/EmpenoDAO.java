package banco.dao;

import banco.models.Empeno;
import java.util.List;
import java.util.Optional;

public interface EmpenoDAO {
    List<Empeno> findAll();
    Optional<Empeno> findById(Integer id);
    Empeno save(Empeno empeno);
    Empeno update(Empeno empeno);
    void deleteById(Integer id);
}