package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service;

import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponCanjeadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.RespuestaCuponCanjeadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.RespuestaCuponeraDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-17
 */
public interface CuponeraService {
    List<CuponDTO> getCupones();

    RespuestaCuponCanjeadoDTO canjear(RespuestaCuponeraDTO respuestaCuponeraDTO);

    List<CuponCanjeadoDTO> cuponesCanjeadosEntreFechas(LocalDate fechaInicial, LocalDate fechaFinal);

    List<CuponDTO> cuponesByCategoriaAndEstado(Integer idCategoria, Integer idEstado);
}
