package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO devuelto con los IDs de los canjes y sus puntos necesarios.")
public class CuponDTO implements Serializable {
    @NotNull
    Integer promoId;
    @NotNull
    Integer puntos;
}
