package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del clasificador de imagen.")
public class ClasificadorImagenDTO {
	private String id;
	private Long idProductoReciclado;
	private Long idImagen;
	private OffsetDateTime createdAt;
    private float scoreValue;    
    private boolean userResponse;
    private String criterioDeAceptacion;
    private String toAction;
}
