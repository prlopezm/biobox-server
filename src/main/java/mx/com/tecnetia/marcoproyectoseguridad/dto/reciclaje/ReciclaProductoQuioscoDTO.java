package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del reciclado de un producto en máquina.")
public class ReciclaProductoQuioscoDTO {
    @NotBlank
    private Long idProducto;
    @NotBlank
    private String foto;
    @NotBlank
    private Long idQuiosco;
    @NotBlank
    private Integer codigoRespuesta;
    @NotBlank
    private Long idUsuario;
}
