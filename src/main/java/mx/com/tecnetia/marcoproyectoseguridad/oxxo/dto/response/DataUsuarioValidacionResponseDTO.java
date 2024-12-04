package mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.response;

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
public class DataUsuarioValidacionResponseDTO {
    private String code;
    private String message;
    private String member_id;
    private String source;
}
