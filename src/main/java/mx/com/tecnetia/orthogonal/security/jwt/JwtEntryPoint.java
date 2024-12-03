package mx.com.tecnetia.orthogonal.security.jwt;

import java.io.IOException;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e)
            throws IOException, ServletException {
        log.error("Fallo en el  método commence de JwtEntryPoint. Credenciales erróneas " + req.getRequestURI());
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Credenciales erróneas.");
    }
}
