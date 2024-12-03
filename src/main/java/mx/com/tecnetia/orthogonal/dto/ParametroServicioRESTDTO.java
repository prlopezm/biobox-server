package mx.com.tecnetia.orthogonal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de los servicios REST de la aplicación.")
public class ParametroServicioRESTDTO {
    private String nombre;
    private String parametro;
}
