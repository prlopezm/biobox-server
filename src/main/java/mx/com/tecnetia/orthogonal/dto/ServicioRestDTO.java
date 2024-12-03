package mx.com.tecnetia.orthogonal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO de los servicios REST de la aplicación.")
public class ServicioRestDTO {
    @NonNull
    private String codigo;
    @NonNull
    private String metodo;
    @NonNull
    private String uri;
    private List<ParametroServicioRESTDTO> parametros;
}
