package mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanjeDTO {
    @NotNull
    @Positive
    private Integer id;
}
