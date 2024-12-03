package mx.com.tecnetia.orthogonal.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "401 No autorizado")
public class UnAuthorizedDTO {
    private LocalDateTime localdatetime;
    private String mensaje;
}
