package mx.com.tecnetia.marcoproyectoseguridad.dto.sms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para el codigo de verificacion de correo")
public class VerificacionEmailDTO {

	@NotNull
	private String email;
	@NotNull
	private String codigo;
	
}
