package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.util.Collection;

import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.FotoProductoRecicladoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.FotoProductoRecicladoListDTO;

public interface ImagenesService {

	FotoProductoRecicladoListDTO consultarFotosProductosReciclados(Collection<Long> ids);
	FotoProductoRecicladoDTO consultarFotoProductoReciclado(Long id);
	
}
