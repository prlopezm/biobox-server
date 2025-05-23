package mx.com.tecnetia.orthogonal.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;

public class UsuarioPrincipal implements UserDetails {

    private static final long serialVersionUID = -7252157924247914354L;
    private Long id;
    private String nombre;
    private String nombreUsuario;
    private String email;
    private String password;
    private String telefono;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(Long id, String nombre, String nombreUsuario, String email, String password, String telefono, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.authorities = authorities;
    }

    public static UsuarioPrincipal build(ArqUsuarioEntity usuario) {
        List<GrantedAuthority> authorities = usuario.getArqUsuarioRolesByIdArqUsuario().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getArqRolByIdArqRol().getNombre()))
                .collect(Collectors.toList());
//        .getArqRolAplicacionEntity().getNombre()
        return new UsuarioPrincipal(usuario.getIdArqUsuario(), usuario.getNombres(), usuario.getNick(),
                usuario.getEmail(), usuario.getPassw(), usuario.getTelefono(), authorities);
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
