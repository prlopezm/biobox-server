package mx.com.tecnetia.orthogonal.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginUsuarioDTO {
    @NotBlank
    @Email(message = "El formato del email es incorrecto.")
    private String nick;
    @NotBlank
    private String passw;
}
