package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.sms.SMS;
import mx.com.tecnetia.marcoproyectoseguridad.util.FormatosUtil;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqUsuarioRepository;
import mx.com.tecnetia.orthogonal.utils.EstatusProcesoEnum;
import mx.com.tecnetia.orthogonal.utils.EstatusVerificacionEnum;
import mx.com.tecnetia.orthogonal.utils.PaisEnum;
import mx.com.tecnetia.orthogonal.utils.sms.TwilioSMSService;

@Service
@RequiredArgsConstructor
@Log4j2
public class VerificacionTelefonoServiceImpl implements VerificacionTelefonoService{
		
	private final TwilioSMSService twilioSMSService;	
	private final ArqUsuarioRepository arqUsuarioRepository;
	private static Map<String, String> otpsOfPhone = new HashMap<String, String>();	
	
	@Override
	public MensajeDTO<?> enviarOTPporSMS(String telefono) {
		telefono = telefono.replace(" ", "");
		log.info("enviarOTPporSMS: " + telefono);
		
		if(!FormatosUtil.numeroTelefonoValido(telefono)) {
			throw new IllegalArgumentException("El número no cumple con el formato de 10 digitos");
		}
		
		String otp = this.getOTP(telefono);
		String mensaje = "Su código de verficación para BioBox es: " + otp;
		SMS sms = new SMS(PaisEnum.MEXICO.getCodigo() + telefono, mensaje);
		String response = twilioSMSService.send(sms);		
		
		if(response != null) {
			log.info("El codigo de verificacion fue enviado correctamente");
			return new MensajeDTO<>(EstatusProcesoEnum.EXITOSO.getValue(), "Código enviado", otp);
		}else {
			log.info("No se envio el codigo de verificacion");			
			throw new IllegalArgumentException("Error al enviar código");
		}
	}
	
	@Override
	public MensajeDTO<?> validarOTPporSMS(String telefono, String codigo) {
		telefono = telefono.replace(" ", "");
		log.info("validarOTP: {} - {}", telefono, codigo);	
		
		if(!FormatosUtil.numeroTelefonoValido(telefono)) {
			throw new IllegalArgumentException("El número no cumple con el formato de 10 digitos");
		}
		
		Optional<ArqUsuarioEntity> usuario = this.arqUsuarioRepository.findByTelefono(telefono);
		boolean valido = codigo.equals(otpsOfPhone.get(telefono.replace(" ", ""))) ? true : false;
		
		if(valido) {
			if(usuario.isPresent()) {
				usuario.get().setTelefonoValidado(true);
				this.arqUsuarioRepository.save(usuario.get());
			}
			otpsOfPhone.remove(telefono);
			log.info("El telefono fue verificado correctamente");
			return new MensajeDTO<>(EstatusProcesoEnum.EXITOSO.getValue(), "Tu teléfono fue verificado");
		}else {
			log.info("El telefono no fue verificado correctamente");			
			throw new IllegalArgumentException("El código es inválido");
		}
	}
	
	@Override
	@Transactional
	public MensajeDTO<?> estatusVerificacion(String telefono) {
		telefono = telefono.replace(" ", "");
		log.info("estatusDeVerificacion: {}", telefono);
		
		if(!FormatosUtil.numeroTelefonoValido(telefono)) {
			throw new IllegalArgumentException("El número no cumple con el formato de 10 digitos");
		}
		
		Optional<ArqUsuarioEntity> usuario = this.arqUsuarioRepository.findByTelefono(telefono);
		
		if(usuario.isPresent()) {
			if(usuario.get().getTelefonoValidado()) {
	    		log.info("El numero de telefono ya fue verificado");
				return new MensajeDTO<>(EstatusVerificacionEnum.VERIFICADO.getValue(), "Tu número de teléfono ya fue verificado.");
			}else {
				log.info("El telefono no esta verificado");
				return new MensajeDTO<>(EstatusVerificacionEnum.NO_VERIFICADO.getValue(), "Verifica tu número de teléfono.");
			}
    	}else {
    		log.info("El telefono no fue encontrado");
    		throw new IllegalArgumentException("No se encontró el número de teléfono.");
    	}
	}
	
	private String getOTP(String telefono) {
		telefono = telefono.replace(" ", "");
		String otp = otpsOfPhone.get(telefono);	
		
		if(otp == null) {
			otp = String.format("%02d", new Random().nextInt(900000) + 100000);
			otpsOfPhone.put(telefono, otp);
		}
		//otpsOfPhone.forEach((key, value) -> System.out.println(key + " " + value));
		return otp;
	}
		
}
