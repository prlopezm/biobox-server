package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del response para validar si un quiosco esta en uso aun.")
public class ResponseQuioscoEnUsoDTO {
    @NotBlank
    private Boolean quioscoEnUso;
}
