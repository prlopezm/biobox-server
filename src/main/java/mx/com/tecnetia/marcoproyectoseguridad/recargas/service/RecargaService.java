package mx.com.tecnetia.marcoproyectoseguridad.recargas.service;

import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.RespuestaCuponCanjeadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.dto.DenominacionRecargaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.dto.RecargaDTO;

import java.util.List;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-23
 */
public interface RecargaService {
    List<DenominacionRecargaDTO> getAllDenominacionRecargaCel();
    RespuestaCuponCanjeadoDTO registrarRecarga(RecargaDTO recarga);
}
