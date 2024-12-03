package mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del mensaje que se obtiene cuando se se pregunta si el celualar está activo en Oxxo.")
public class MensajeOxxoDTO {
    private String url;
    private boolean activo;
}
