package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del request del servicio para validar la foto con el algoritmo.")
public class RequestValidaProductoFotoDTO {
	private Long idProductoReciclado;
	private String clase;
	private String label;
//    private byte[] imageBase64;
	private String imageBase64;
}
