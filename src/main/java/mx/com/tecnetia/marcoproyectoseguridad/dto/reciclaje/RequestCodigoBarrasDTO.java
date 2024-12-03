package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del request del servicio obtener un codigo de barras de la foto de un producto.")
public class RequestCodigoBarrasDTO {
    private String image;
}
