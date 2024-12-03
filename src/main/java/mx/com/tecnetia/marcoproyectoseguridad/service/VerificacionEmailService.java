package mx.com.tecnetia.marcoproyectoseguridad.service;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;

public interface VerificacionEmailService {

	MensajeDTO<?> enviarLinkVerificacion(String email);
	String validarLinkVerificacion(String token);
	MensajeDTO<?> enviarOTP(String email);	
	MensajeDTO<?> validarOTP(String email, String codigo);
	MensajeDTO<?> estatusVerificacion(String email);
	
}
