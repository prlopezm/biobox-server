package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service;

import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponCanjeadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponDTO;

import java.util.List;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-17
 */
public interface CuponeraService {
    List<CuponDTO> getCupones();

    CuponCanjeadoDTO canjear(Integer promoId);
}
