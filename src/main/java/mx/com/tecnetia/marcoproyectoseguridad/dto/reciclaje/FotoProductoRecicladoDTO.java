package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de foto de un producto reciclado.")
public class FotoProductoRecicladoDTO {

	private Long idFotoProductoReciclado;
	private Long idProductoReciclado;
	private byte[] foto;
	private Boolean exitoso;
	
}
