package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-31 11:34 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para el histórico de canjes distintos a los prontipagos.")
public class HistoricoCanjeDTO {
    private String canjeNombre;
    private Integer puntosUsados;
}
