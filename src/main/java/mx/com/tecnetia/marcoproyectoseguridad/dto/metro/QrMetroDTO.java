package mx.com.tecnetia.marcoproyectoseguridad.dto.metro;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de QR generado para acceso del metro.")
public class QrMetroDTO {
    private String barCode;
    private LocalDateTime fechaCreacion;
    private Long idEstacionMetro;

}
