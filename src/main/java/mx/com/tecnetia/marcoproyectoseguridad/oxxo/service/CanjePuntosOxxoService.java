package mx.com.tecnetia.marcoproyectoseguridad.oxxo.service;

import jakarta.validation.constraints.NotNull;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-04
 */
public interface CanjePuntosOxxoService {
    String canjearPuntos(@NotNull Integer id);
}
