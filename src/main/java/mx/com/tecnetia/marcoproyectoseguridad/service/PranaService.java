package mx.com.tecnetia.marcoproyectoseguridad.service;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;

public interface PranaService {

	MensajeDTO<?> canjearCodigoDescuento(String sku, Long idPuntosRequeridos, Long idUsuario);
	
}
