package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de puntos del usuario.")
public class PuntosUsuarioDTO {
    private Integer puntosUsuario;
    private List<ProgramaCategoriaDTO> puntosProgramasLealtad;

    public PuntosUsuarioDTO(Integer puntosUsuario) {
        this.puntosUsuario = puntosUsuario;
    }
}
