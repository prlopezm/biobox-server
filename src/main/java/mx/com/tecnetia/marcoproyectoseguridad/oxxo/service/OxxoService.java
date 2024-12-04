package mx.com.tecnetia.marcoproyectoseguridad.oxxo.service;

import jakarta.validation.constraints.NotNull;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.MenuOxxoDTO;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-03
 */
public interface OxxoService {
    MenuOxxoDTO obtenerMenu();
    String canjearPuntos(@NotNull Integer id);
}
