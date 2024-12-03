package mx.com.tecnetia.orthogonal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la creación de un nuevo usuario con un rol determinado. Solo un administrador puede crear este tipo de usuarios.")
public class NuevoUsuarioArquitecturaRolDTO extends NuevoUsuarioArquitecturaDTO{
    @NotEmpty(message = "El nuevo usuario debe contener al menos un rol.")
    private List<String> roles;
}
