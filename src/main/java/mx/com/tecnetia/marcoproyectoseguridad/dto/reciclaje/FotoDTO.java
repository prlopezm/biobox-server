package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para enviar a validar una foto al servicio de IA.")
public class FotoDTO {

    private MultipartFile file;
}
