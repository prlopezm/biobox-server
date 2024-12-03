package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.util.Constantes;
import mx.com.tecnetia.marcoproyectoseguridad.util.ConstantesProgramaLealtad;
import mx.com.tecnetia.marcoproyectoseguridad.util.DescuentosUtil;

@Service
@RequiredArgsConstructor
public class BacardiServiceImpl implements BacardiService {

	private final ProgramaService programaService;
	
	@Override
	@Transactional
	@SuppressWarnings("unused")
	public MensajeDTO<?> canjearCodigoDescuento(String sku, Long idPuntosRequeridos, Long idUsuario) {
		
		HashMap<String, Object> vars = new HashMap<String, Object>();
		boolean usuarioPuedeCanjear = true;
		boolean canjeUnico = false;
		boolean usuarioTieneCodigo = false;
		boolean categoriaVigente = false;
		boolean programaVigente = false;
		Long idTipoDescuento;
		int puntosUsuario = 0;
		int puntosCoste = 0;
		int puntosLimiteInferior = 0;
		int puntosLimiteSuperior = 0;
		
        try {
        	
        	//Validamos si la categoria es de canje unico
        	vars.putAll(programaService.validaCanje(idPuntosRequeridos, idUsuario));
        	
        	idTipoDescuento = (Long) (vars.get(ConstantesProgramaLealtad.KEY_TIPO_DESCUENTO));
        	canjeUnico = (boolean) vars.get(ConstantesProgramaLealtad.KEY_CATEGORIA_CANJE_UNICO);
        	usuarioTieneCodigo = (boolean) vars.get(ConstantesProgramaLealtad.KEY_USUARIO_TIENE_CODIGO);
        	categoriaVigente = (boolean) vars.get(ConstantesProgramaLealtad.KEY_CATEGORIA_VIGENTE);
        	programaVigente = (boolean) vars.get(ConstantesProgramaLealtad.KEY_PROGRAMA_VIGENTE);
        	puntosUsuario = (int) (vars.get(ConstantesProgramaLealtad.KEY_PUNTOS_USUARIO));
    		puntosCoste = (int) (vars.get(ConstantesProgramaLealtad.KEY_PUNTOS_COSTE_SUBPROGRAMA));
        	puntosLimiteInferior = (int) vars.get(ConstantesProgramaLealtad.KEY_PUNTOS_LIMITE_INFERIOR);
        	puntosLimiteSuperior = (int) vars.get(ConstantesProgramaLealtad.KEY_PUNTOS_LIMITE_SUPERIOR);
        	usuarioPuedeCanjear = (canjeUnico && usuarioTieneCodigo) ? false : true;        	
        	
        	if(!categoriaVigente || !programaVigente) {
            	return new MensajeDTO<>("El programa no se encuentra vigente.");
            }
            if(puntosUsuario < puntosLimiteInferior) {
            	return new MensajeDTO<>("No cuentas con puntos suficientes.");
            } 
            if (!usuarioPuedeCanjear){
            	return new MensajeDTO<>("No se puede realizar más de 1 canje por usuario.");
	        }        	
			        
            //Realizamos el descuento de los puntos redimidos del usuario
			vars.putAll(programaService.descuentoDePuntos(idPuntosRequeridos, idUsuario));	        	
			//Obtenemos cupon de descuento
			vars.putAll(programaService.obtencionDeCodigo(idUsuario, idPuntosRequeridos, canjeUnico));	
			
			//Enviamos email al usuario
			String descuento = DescuentosUtil.formatoValor(idTipoDescuento, (BigDecimal) vars.get(ConstantesProgramaLealtad.KEY_VALOR_DESCUENTO_N),
																	  (BigDecimal) vars.get(ConstantesProgramaLealtad.KEY_VALOR_DESCUENTO_X));						
			
			programaService.enviarCodigoDescuentoPorMail(idUsuario, Constantes.PLANTILLA_EMAIL_DESCUENTO_POR_CODIGO, descuento,
				    			                         (String) vars.get(ConstantesProgramaLealtad.KEY_CODIGO_DESCUENTO),				    			                         
				    			                         (String) vars.get(ConstantesProgramaLealtad.KEY_IMAGEN_DESCUENTO),				    			                         
				    	 								 (String) vars.get(ConstantesProgramaLealtad.KEY_NOMBRE_CATEGORIA),				    	 								 
				    	 								 (String) vars.get(ConstantesProgramaLealtad.KEY_LOGO_2_CATEGORIA),
				       									 (String) vars.get(ConstantesProgramaLealtad.KEY_CATEGORIA_PORTAL));
			
			return new MensajeDTO<>("Tus puntos se han canjeado y en breve recibirás un email con la información para hacer válido tu beneficio.");				    	        	
	        	
        }catch(Exception e) {
        	e.printStackTrace();
        	throw new IllegalArgumentException("No se pudo realizar el canje de tus puntos.");
        }
        
	}	
	

}
