package mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de líneas del metro.")
public class LineaDTO {
    private Integer idLinea;
    private Integer numero;
    private String color;
    private byte[] foto;
    private List<EstacionMetroDTO> estaciones;

    public LineaDTO(Integer idLinea, Integer numero, String color, byte[] foto) {
        this.idLinea = idLinea;
        this.numero = numero;
        this.color = color;
        this.foto = foto;
    }
}
