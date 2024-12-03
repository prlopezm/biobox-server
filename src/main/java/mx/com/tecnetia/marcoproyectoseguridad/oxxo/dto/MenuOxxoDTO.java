package mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de las opciones de Oxxo.")
public class MenuOxxoDTO {
    private int puntosRestantes;
    private String leyenda;
    private List<OpcionCanjeOxxoDTO> opciones;
}
