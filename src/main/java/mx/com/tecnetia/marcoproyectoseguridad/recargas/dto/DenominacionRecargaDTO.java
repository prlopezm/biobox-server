package mx.com.tecnetia.marcoproyectoseguridad.recargas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO con los datos de las posibles recargas de celular")
public class DenominacionRecargaDTO {
    private Integer idDenominacionRecargaDTO;
    private String nombreCompania;
    private byte[] imgLogo;
    private Integer idProduct;
    private Integer monto;
    private Integer puntos;
}
