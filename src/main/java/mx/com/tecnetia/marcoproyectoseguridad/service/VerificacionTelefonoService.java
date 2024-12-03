package mx.com.tecnetia.marcoproyectoseguridad.service;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;


public interface VerificacionTelefonoService {

	MensajeDTO<?> enviarOTPporSMS(String telefono);	
	MensajeDTO<?> validarOTPporSMS(String telefono, String codigo);
	MensajeDTO<?> estatusVerificacion(String telefono);
	
}
