package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO devuelto luego de efectuar un canje de un cupón en cuponerapp")
public class RespuestaCuponCanjeadoDTO {
    private String mensaje;
    private Integer puntosRestantes;
}
