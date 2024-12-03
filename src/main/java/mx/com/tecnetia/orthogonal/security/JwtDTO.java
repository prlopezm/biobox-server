package mx.com.tecnetia.orthogonal.security;

import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.LinksDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.metro.ContratoMetroDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO;
import org.springframework.security.core.GrantedAuthority;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para el login del usuario. Dependiendo del tipo de usuario, el campo correspondiente contendrá la información del usuario que se logea.")
public class JwtDTO {
    @NotBlank
    private String token;
    @NotBlank
    private String bearer = "Bearer";
    @NotBlank
    private String nick;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apPaterno;
    @NotBlank
    private String apMaterno;
    @NotBlank
    private String email;
    @NotBlank
    private String rol;
    @NotBlank
    private String telefono;        
    @NotBlank
    private List<PuntosColorDTO> puntosList;
    @NotBlank
    private byte[] imagen;
    @NotEmpty
    private Collection<? extends GrantedAuthority> authorities;
    @NotBlank
    private ContratoMetroDTO contratoMetro;
    @NotBlank
    private LinksDTO links;
    private boolean en_revision;
    private boolean emailValidado;
    private boolean telefonoValidado;
    private boolean registroConcluido;
    private String mensaje;
    private Boolean oxxoActivo = true;

    public JwtDTO(String token, String nick, String nombre, String apPaterno, String apMaterno, String email, String rol, String telefono,
                  List<PuntosColorDTO> puntosList,
                  byte[] imagen, Collection<? extends GrantedAuthority> authorities,
                  ContratoMetroDTO contratoMetro, LinksDTO links, boolean en_revision,
                  boolean emailValidado, boolean telefonoValidado, boolean registroConcluido, String mensaje) {
        this.token = token;
        this.nick = nick;
        this.authorities = authorities;
        this.nombre = nombre;
        this.puntosList = puntosList;
        this.imagen = imagen;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.email = email;       
        this.rol = rol;
        this.telefono = telefono;        
        this.contratoMetro = contratoMetro;
        this.links = links;
        this.en_revision = en_revision;
        this.emailValidado = emailValidado;
        this.telefonoValidado = telefonoValidado;
        this.registroConcluido = registroConcluido;
        this.mensaje = mensaje;
    }


}
