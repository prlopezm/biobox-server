package mx.com.tecnetia.orthogonal.security;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginUsuarioDTO {
    @NotBlank
    private String nick;
    @NotBlank
    private String passw;
}
