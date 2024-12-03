package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de puntos requeridos.")
public class PuntosRequeridosDTO {
    
	private Long idPuntosRequeridos;
    private Long idPrograma;
    private Integer puntosLimiteInferior;
    private Integer puntosLimiteSuperior;
    private Integer puntosCoste;

}
