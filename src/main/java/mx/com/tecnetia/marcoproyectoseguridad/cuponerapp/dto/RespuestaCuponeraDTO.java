package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-31 10:06 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para notificar el resultado de un canje en cuponerapp.")
public class RespuestaCuponeraDTO {
    private Integer promoId;
    private String folio;
    private String fecha;
    private String hora;
    private String codigoQr;
    private String imagenBase64;
    private String costo;
    private String nombre;
}
