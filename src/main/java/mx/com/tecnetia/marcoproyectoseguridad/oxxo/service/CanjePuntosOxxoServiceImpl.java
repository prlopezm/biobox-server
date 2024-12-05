package mx.com.tecnetia.marcoproyectoseguridad.oxxo.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-04
 */
public class CanjePuntosOxxoServiceImpl implements CanjePuntosOxxoService {
    @Override
    @Transactional
    public String canjearPuntos(@NotNull Integer id) {
        return "";
    }
}
