package mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de quioscos.")
public class QuioscoDTO {
    private Long idQuiosco;
    private String direccion;
    private String latitud;
    private String longitud;
    private String nombre;
}
