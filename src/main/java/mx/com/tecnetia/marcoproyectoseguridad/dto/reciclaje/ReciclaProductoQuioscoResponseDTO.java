package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del reciclado de un producto en máquina.")
public class ReciclaProductoQuioscoResponseDTO {
    @NotNull
    private Long idProducto;
    private String foto;
    @NotNull
    private Long idQuiosco;
    @NotNull
    private Integer codigoRespuesta;
    @NotNull
    private Long idUsuario;
    private Integer peso;
    @NotBlank
    private String barCode;
}
