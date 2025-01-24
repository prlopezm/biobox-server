package mx.com.tecnetia.orthogonal.security.auth;

import java.util.Iterator;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.LinksDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.metro.ContratoMetroDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.service.VerificacionEmailService;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioRolEntity;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqPropiedadEntityRepository;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqUsuarioRepository;
import mx.com.tecnetia.orthogonal.security.JwtDTO;
import mx.com.tecnetia.orthogonal.security.LoginUsuarioDTO;
import mx.com.tecnetia.orthogonal.security.jwt.JwtProvider;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import mx.com.tecnetia.orthogonal.utils.EstatusVerificacionEnum;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final ArqUsuarioRepository usuarioEntityRepository;
    private final JwtProvider jwtProvider;
    private final UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;
    private final UsuarioService usuarioService;
    private final ArqPropiedadEntityRepository arqPropiedadEntityRepository;
    private final Environment environment;
    private final VerificacionEmailService verificacionEmailService;

    @Override
    @Transactional(readOnly = true)
    public JwtDTO login(LoginUsuarioDTO loginUsuario) {
        String saleAgent = environment.getProperty("stc.contract.data.sale.agent");
        String serialNumber = environment.getProperty("stc.contract.serial.number");
        String saleDate = environment.getProperty("stc.contract.sale.date");
        String linkAyuda = environment.getProperty("link.ayuda");
        String linkMasInfo = environment.getProperty("link.mas.info");
        String linkAvisoPrivacidad = environment.getProperty("link.aviso.privacidad");

        var usuarioEntity = this.usuarioEntityRepository.findByNick(loginUsuario.getNick().toLowerCase().trim())
                .orElseThrow(() -> new IllegalArgumentException("El correo electrónico no existe"));
        if(usuarioEntity.getNuevoIngreso()){
            throw new IllegalArgumentException("Debes confirmar tu email antes.");
        }
        if (Boolean.TRUE.equals(usuarioEntity.getActivo())) {
            Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginUsuario.getNick().toLowerCase().trim(), loginUsuario.getPassw()));
            } catch (AuthenticationException ex) {
                throw new IllegalArgumentException("El usuario o la contraseña proporcionados no son válidos.");
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String rol = "";
            if(!usuarioEntity.getArqUsuarioRolesByIdArqUsuario().isEmpty()) {
                Iterator<ArqUsuarioRolEntity> rolEntityIt = usuarioEntity.getArqUsuarioRolesByIdArqUsuario().iterator();
                rol = rolEntityIt.hasNext() ? rolEntityIt.next().getArqRolByIdArqRol().getNombre() : "";
            }

            ContratoMetroDTO contratoMetroDTO = new ContratoMetroDTO();
            LinksDTO linksDTO = new LinksDTO();

            contratoMetroDTO.setDataSaleAgent(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(saleAgent)
                    .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + saleAgent)).getValor());
            contratoMetroDTO.setSerialNumber(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(serialNumber)
                    .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + serialNumber)).getValor());
            contratoMetroDTO.setSaleDate(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(saleDate)
                    .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + saleDate)).getValor());
            linksDTO.setAyuda(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(linkAyuda)
                    .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + linkAyuda)).getValor());
            linksDTO.setMasInfo(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(linkMasInfo)
                    .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + linkMasInfo)).getValor());
            linksDTO.setAvisoPrivacidad(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(linkAvisoPrivacidad)
                    .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + linkAvisoPrivacidad)).getValor());
            
            boolean registroConcluido = this.usuarioService.registroConcluido(usuarioEntity);
            
            //Valida si el email esta verificado
            if(!usuarioEntity.getRegistroConcluido() && 
            		verificacionEmailService.estatusVerificacion(
            				usuarioEntity.getEmail().toLowerCase()).equals(EstatusVerificacionEnum.NO_VERIFICADO.getValue())
            	) {            	
		            	verificacionEmailService.enviarLinkVerificacion(usuarioEntity.getEmail().toLowerCase());
		        		throw new IllegalArgumentException("Por favor verifica tu cuenta de usuario, se envio un link de verificación a tu correo electrónico.");
            }
                        
            JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), usuarioEntity.getNombres(), usuarioEntity.getApellidoPaterno(),
				                    usuarioEntity.getApellidoMaterno(), usuarioEntity.getEmail(), rol , usuarioEntity.getTelefono(),                    
				                    this.usuarioPuntosColorEntityRepository.getDTOByUsuario(usuarioEntity.getIdArqUsuario()),
				                    usuarioEntity.getFotoUsuarioEntity().getFoto(),
				                    userDetails.getAuthorities(),contratoMetroDTO, linksDTO, true,
				                    usuarioEntity.getEmailValidado(), usuarioEntity.getTelefonoValidado(), registroConcluido, "");            
            return jwtDTO;
        }
        throw new IllegalArgumentException("La cuenta de usuario se encuentra deshabilitada.");
    }
    
}
