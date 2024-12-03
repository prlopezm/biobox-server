package mx.com.tecnetia.orthogonal.dto.notificaciones;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO contenido en la notificacion de firebase.")
public class DataDTO {
    private Integer step;
}
