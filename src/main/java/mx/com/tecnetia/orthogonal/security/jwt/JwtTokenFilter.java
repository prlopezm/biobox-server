package mx.com.tecnetia.orthogonal.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import mx.com.tecnetia.orthogonal.security.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.orthogonal.security.UserDetailsServiceImpl;

@Log4j2
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getToken(req);
            if (token != null && jwtProvider.validateToken(token)) {
                String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
                var authoritiesList = jwtProvider.getAuthorities(token);
                var idUsuario = jwtProvider.getIdUsuarioFromToken(token);
                var telefono = jwtProvider.getTelefonoUsuarioFromToken(token);
                List<GrantedAuthority> authorities = authoritiesList.stream()
                        .map(rol -> new SimpleGrantedAuthority((String) rol))
                        .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(new UsuarioPrincipal(idUsuario, nombreUsuario, nombreUsuario, nombreUsuario, null, telefono, authorities), null,
                        authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            log.error("Fallo en el método doFilter {}", e.getMessage());
        }
        filterChain.doFilter(req, res);
    }

    private String getToken(HttpServletRequest request) {
        String authReq = request.getHeader("Authorization");
        if (authReq != null && authReq.startsWith("Bearer "))
            return authReq.replace("Bearer ", "").trim();
        return null;
    }
}
