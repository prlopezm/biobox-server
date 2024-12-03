package mx.com.tecnetia.orthogonal.security.auth;

import mx.com.tecnetia.orthogonal.security.JwtDTO;
import mx.com.tecnetia.orthogonal.security.LoginUsuarioDTO;

public interface AuthService {
	
    JwtDTO login(LoginUsuarioDTO loginUsuario);

}
