package mx.com.tecnetia.orthogonal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de los contenidos de la aplicación, mensajes, textos, fotos etc.")
public class ContenidoDTO {
    @Schema(description = "Código único para cada contenido, es sensible a mayúsculas.")
    @NotNull
    @Size(min = 1, max = 100)
    private String codigo;
    @NotNull
    @Schema(description = "Texto del contenido. En el caso que el objetivo sea obtener una imagen, debe contener el texto alternativo")
    @Size(min = 1, max = 2000)
    private String mensaje;
    @Schema(description = "Imagen en formato Base64. Este campo es opcional.")
    private String media;
}
