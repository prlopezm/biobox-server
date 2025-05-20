package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de un abrir quiosco.")
public class AbrirQuioscoDTO {
    @NotNull
    private Long quioscoId;

    private Long usuarioId;

    private int tipoPic;
}
