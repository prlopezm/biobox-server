package mx.com.tecnetia.orthogonal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;
import mx.com.tecnetia.orthogonal.services.UsuarioService;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        ArqUsuarioEntity usuario = usuarioService.getByNick(nombreUsuario).orElseThrow(()-> new IllegalArgumentException("El nick especificado no existe"));
        return UsuarioPrincipal.build(usuario);
    }

}