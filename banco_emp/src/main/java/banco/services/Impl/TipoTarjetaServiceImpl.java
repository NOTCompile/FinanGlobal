package banco.services.Impl;

import banco.dao.TipoTarjetaDAO;
import banco.models.TipoTarjeta;
import banco.services.TipoTarjetaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoTarjetaServiceImpl implements TipoTarjetaService {

    private final TipoTarjetaDAO tipoTarjetaDAO;

    public TipoTarjetaServiceImpl(TipoTarjetaDAO tipoTarjetaDAO) {
        this.tipoTarjetaDAO = tipoTarjetaDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoTarjeta> findAll() {
        return tipoTarjetaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoTarjeta> findById(Integer id) {
        return tipoTarjetaDAO.findById(id);
    }

    @Override
    @Transactional
    public TipoTarjeta save(TipoTarjeta tipoTarjeta) {
        return tipoTarjetaDAO.save(tipoTarjeta);
    }

    @Override
    @Transactional
    public TipoTarjeta update(TipoTarjeta tipoTarjeta) {
        return tipoTarjetaDAO.update(tipoTarjeta);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        tipoTarjetaDAO.deleteById(id);
    }
}

