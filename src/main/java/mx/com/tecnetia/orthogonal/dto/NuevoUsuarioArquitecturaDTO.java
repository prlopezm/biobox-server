package mx.com.tecnetia.orthogonal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la creación de un nuevo usuario. El usuario se crea con el rol predefinido de menos permisos.")
public class NuevoUsuarioArquitecturaDTO {
    @NotBlank(message = "Los nombres son obligatorios.")
    @Size(min = 1, max = 200)
    private String nombres;
    @Size(max = 100)
    @NotBlank(message = "El apellido paterno es obligatorio.")
    private String apellidoPaterno;
    @Size(max = 100)
    private String apellidoMaterno;
    @Email
    @Size(max = 100)
    @NotBlank(message = "El email es obligatorio.")
    private String email;
    @NotBlank(message = "El nick es obligatorio.")
    @Size(min = 1, max = 200)
    private String nick;
    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 1, max = 1000)
    private String passw;
}
