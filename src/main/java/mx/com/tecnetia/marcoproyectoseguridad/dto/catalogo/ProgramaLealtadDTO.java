package mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de programas de lealtad.")
public class ProgramaLealtadDTO {
    private Integer idPrograma;
    private String logoUrl;
    private String nombre;
    private String fondo;
    private String tipoPrograma;

    public ProgramaLealtadDTO(Integer idLinea, Integer numero, String color, byte[] foto) {
       // this.idLinea = idLinea;
        //this.numero = numero;
        //this.color = color;
      //  this.foto = foto;
    }
}
