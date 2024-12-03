package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Lista DTO de fotos de productos reciclados.")
public class FotoProductoRecicladoListDTO {
	
	private List<FotoProductoRecicladoDTO> fotos;

}
