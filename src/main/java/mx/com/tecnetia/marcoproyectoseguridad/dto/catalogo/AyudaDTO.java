package mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de ayuda.")
public class AyudaDTO {
    private Integer idAyuda;
    private String nombre;
    private String urlFoto;
    private String texto;
}
