package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de puntos para un color.")
public class PuntosColorTmpDTO {
    private Integer idColor;
    private String nombreColor;
    private String color;
    private Integer puntos;
    private Integer montoCentavos;
    private String urlFoto;
    private String urlFoto2;

}
