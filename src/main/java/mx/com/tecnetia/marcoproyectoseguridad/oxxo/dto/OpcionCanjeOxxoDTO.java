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
@Schema(description = "DTO de las opciones canje de Oxxo.")
public class OpcionCanjeOxxoDTO {
    private int id;
    private String nombre;
}
