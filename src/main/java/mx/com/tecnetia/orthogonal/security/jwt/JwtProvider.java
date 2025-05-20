package mx.com.tecnetia.orthogonal.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.orthogonal.security.UsuarioPrincipal;


@Component
@Log4j2
public class JwtProvider {

//    @PostConstruct
//    public void init() {
//        this.key = io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS512);
//    }

    @PostConstruct
    public void init() {
        this.key = getSingnInKey();
    }

    //    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private Key key;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        var expirationDate = new Date(new Date().getTime() + expiration * 1000L);
        log.info("El token generado expirará en {}", expirationDate.toString());
        return Jwts.builder().setSubject(usuarioPrincipal.getUsername()).setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(this.key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getNombreUsuarioFromToken(String token) {
        var usuario = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().getSubject();
        log.info("Usuario en el token: {}", usuario);
        return usuario;
    }

    public Long getIdUsuarioFromToken(String token) {
        var idUsuario = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().get("idUsuario");
        return Long.valueOf(idUsuario.toString());
    }

    public String getTelefonoUsuarioFromToken(String token) {
        var telefono = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().get("telefono");
        return telefono.toString();
    }

    public List<?> getAuthorities(String token) {
        var authorities = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().get("roles");
        return (List<?>) authorities;
    }

    private Key getSingnInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("token mal formado {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("token no portado {} ", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("token expirado {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("token ilegal {}", e.getMessage());
        } catch (SecurityException e) {
            log.error("error en firma {}", e.getMessage());
        } catch (Exception e) {
            log.error("Excepción general: {}", e.getMessage());
        }
        return false;
    }
}
