package mx.com.tecnetia.orthogonal.services;

import java.util.Optional;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.orthogonal.dto.EditaUsuarioArquitecturaDTO;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;
import mx.com.tecnetia.orthogonal.security.EstatusUsuarioDTO;
import mx.com.tecnetia.orthogonal.security.NuevoUsuarioArquitecturaDTO;

@SuppressWarnings("rawtypes")
public interface UsuarioService {
	

	ArqUsuarioEntity findById(Long id);
		
    ArqUsuarioEntity getUsuarioLogeado();
    
	Optional<ArqUsuarioEntity> getByNick(String nu);

    Optional<ArqUsuarioEntity> getByEmail(String email);
    
    Optional<ArqUsuarioEntity> getByTelefono(String telefono);
    
    MensajeDTO crearUsuario(NuevoUsuarioArquitecturaDTO nuevoUsuario, byte[] foto, String nombreFoto);
    
    MensajeDTO editarUsuario(EditaUsuarioArquitecturaDTO datos);

    MensajeDTO editarContrasenia(String correo, String passw);

    void editarContraseniaPendienteConfirmacion(String correo, String contrasenia);
    
    void recuperarContrasenia(String email);
    
    void desactivarUsuario(String correo);    
    EstatusUsuarioDTO existeUsuario(String nick);
    
    String estatusVerificacion(ArqUsuarioEntity usuario);
    
    boolean registroConcluido(ArqUsuarioEntity usuario);
        
}
