package mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de estaciones de metro.")
public class EstacionMetroDTO {
    private Long idEstacionMetro;
    private String nombre;
    private String latitud;
    private String longitud;
    private byte[] foto;
    private String codigo;
}
