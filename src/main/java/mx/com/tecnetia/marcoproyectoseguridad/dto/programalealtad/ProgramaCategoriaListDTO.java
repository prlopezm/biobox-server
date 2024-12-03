package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de equivalencia de puntos por programa de lealtad.")
public class ProgramaCategoriaListDTO {
    private List<ProgramaCategoriaDTO> programasLealtad;

}
