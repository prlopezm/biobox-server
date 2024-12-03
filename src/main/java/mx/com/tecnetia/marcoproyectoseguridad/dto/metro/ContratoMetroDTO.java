package mx.com.tecnetia.marcoproyectoseguridad.dto.metro;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de datos del contrato con el metro.")
public class ContratoMetroDTO {
    private String dataSaleAgent;
    private String serialNumber;
    private String saleDate;

}
