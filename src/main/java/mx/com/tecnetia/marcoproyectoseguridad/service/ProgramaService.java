package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.util.HashMap;

public interface ProgramaService {

	HashMap<String, Object> validaCanje(Long idPuntosRequeridos, Long idUsuario);
	HashMap<String, Object> descuentoDePuntos(Long idPuntosRequeridos, Long idUsuario);
	HashMap<String, Object> obtencionDeCodigo(Long idUsuario, Long idPuntosRequeridos, boolean canjeUnico);
	void enviarCodigoDescuentoPorMail(Long idUsuario, String plantilla, String descuento, String codigoDescuento, String imagenDescuento, String nombreCategoria, String logoCategoria, String urlPortal);
	
}
