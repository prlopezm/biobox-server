package mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de ayuda.")
public class AyudasListDTO {
    private List<AyudaDTO> ayudas;
}
