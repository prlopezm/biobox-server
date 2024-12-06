package mx.com.tecnetia.orthogonal.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditaUsuarioArquitecturaDTO {
    @NotBlank(message = "Nombre(s) obligatorio(s).")
    @Size(max = 200, message = "El tamaño máximo del nombre es 200")
    private String nombres;
    @Size(max = 100, message = "El tamaño máximo del apellido paterno es 100")
    @NotBlank(message = "El apellido paterno es obligatorio.")
    private String apellidoPaterno;
    @Size(max = 100, message = "El tamaño máximo del apellido materno es 100")
    @NotBlank(message = "El apellido materno es obligatorio.")
    private String apellidoMaterno;
    @Email(message = "No es un email válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    @NotBlank(message = "El email es obligatorio.")
    private String email;
    @NotBlank(message = "El nick es obligatorio.")
    @Size(min = 1, max = 200)
    private String nick;
    @NotBlank(message = "El teléfono es obligatorio.")
    @Size(max = 10, min = 10, message = "El número de tefono debe ser de 10 digitos")
    @Digits(fraction = 0, integer = 10, message = "El número de tefono solo debe contener digitos")
    @NotBlank(message = "El teléfono es obligatorio.")
    private String telefono;
    private boolean emailValidado;
    private boolean telefonoValidado;
}
