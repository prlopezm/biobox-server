package mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity.OpcionCanjeOxxoEntity}
 */
@Value
public class OpcionCanjeOxxoEntityDTO implements Serializable {
    @NotNull
    Integer id;
    @NotBlank
    String nombre;
}
