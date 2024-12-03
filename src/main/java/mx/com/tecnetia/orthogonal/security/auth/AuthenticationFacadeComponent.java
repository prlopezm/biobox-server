package mx.com.tecnetia.orthogonal.security.auth;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacadeComponent {
    Authentication getAuthentication();
}
