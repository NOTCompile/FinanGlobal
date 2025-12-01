package banco.services.Impl;

import banco.dao.TarjetaDAO;
import banco.models.Tarjeta;
import banco.services.TarjetaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    private final TarjetaDAO tarjetaDAO;

    public TarjetaServiceImpl(TarjetaDAO tarjetaDAO) {
        this.tarjetaDAO = tarjetaDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tarjeta> findAll() {
        return tarjetaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tarjeta> findById(Integer id) {
        return tarjetaDAO.findById(id);
    }

    @Override
    @Transactional
    public Tarjeta save(Tarjeta tarjeta) {
        return tarjetaDAO.save(tarjeta);
    }

    @Override
    @Transactional
    public Tarjeta update(Tarjeta tarjeta) {
        return tarjetaDAO.update(tarjeta);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        tarjetaDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tarjeta> findByCuentaId(Integer idCuenta) {
        return tarjetaDAO.findByCuentaId(idCuenta);
    }
}

