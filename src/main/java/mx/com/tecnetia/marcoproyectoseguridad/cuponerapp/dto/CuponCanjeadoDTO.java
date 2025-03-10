package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-31 10:28 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Cupón canjeado a devolver en la consulta dado un rango de fechas.")
public class CuponCanjeadoDTO {
    private Integer idPromocion;
    private String nombrePromocion;
    private Integer puntosUsados;
    private LocalDateTime fecha;
    private String folio;
    private String codigoQr;
    private String imagenBase64;
}
