package mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.response;

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
public class ValidacionUsuarioResponseDTO {
    private String status;
    private DataUsuarioValidacionResponseDTO data;
}
