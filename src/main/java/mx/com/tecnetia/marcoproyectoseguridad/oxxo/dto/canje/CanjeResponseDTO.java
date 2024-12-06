package mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.canje;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanjeResponseDTO {
    private String status;
    private DataCanjeResponseDTO data;
}
