package mx.com.tecnetia.orthogonal.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevoUsuarioArquitecturaDTO {
    @NotBlank(message = "Nombre(s) obligatorio(s).")
    @Size(max = 200, message = "El tamaño máximo del nombre es 200")
    private String nombres;
    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 1, max = 1000)
    private String passw;
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
    @NotBlank(message = "El nuevo usuario debe contener el rol.")
    private String rol;    
    @NotBlank(message = "El teléfono es obligatorio.")
    @Size(max = 10, min = 10, message = "El número de tefono debe ser de 10 digitos")
    @Digits(fraction = 0, integer = 10, message = "El número de tefono solo debe contener digitos")
    @NotBlank(message = "El teléfono es obligatorio.")
    private String telefono;    
}
