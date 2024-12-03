package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de estado de cuenta general de un usuario.")
public class MovimientosListDTO {
    private String nombre;
    private String paterno;
    private String materno;
    private String nick;
    private List<PuntosColorDTO> puntosActuales;
    private List<MovimientosDTO> ultimosMovimientos;

}
