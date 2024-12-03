package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del response del servicio para validar la foto con el algoritmo.")
public class ResponseValidaFotoDTO {
    @NotBlank
    private String barcode;
    @NotBlank
    @JsonProperty("class")
    private String clase;
    @NotBlank
    private String image;
    @NotNull
    private BigDecimal realibilityScore;
}
