package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Optional;

import mx.com.tecnetia.marcoproyectoseguridad.util.FechasUtil;
import mx.com.tecnetia.orthogonal.ampq.ActualizaPuntosEventoProducer;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ColorEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaCodigosDescuentoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaProntipagoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaPuntosRequeridosEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ServicioUsadoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorConsumidosEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ProgramaCodigosDescuentoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ProgramaProntipagoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ProgramaPuntosRequeridosEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ServicioUsadoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorConsumidosEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.util.Constantes;
import mx.com.tecnetia.marcoproyectoseguridad.util.ConstantesProgramaLealtad;
import mx.com.tecnetia.orthogonal.dto.MailDTO;
import mx.com.tecnetia.orthogonal.utils.email.EmailOperationsThymeleafService;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProgramaServiceImpl implements ProgramaService {

	private final ServicioUsadoEntityRepository servicioUsadoEntityRepository;	
	private final EmailOperationsThymeleafService emailOperationsThymeleafService;
	private final ProgramaProntipagoEntityRepository programaProntipagoEntityRepository;
	private final UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;
	private final ProgramaCodigosDescuentoEntityRepository programaCodigosDescuentoEntityRepository;
	private final ProgramaPuntosRequeridosEntityRepository programaPuntosRequeridosEntityRepository;
	private final UsuarioPuntosColorConsumidosEntityRepository usuarioPuntosColorConsumidosEntityRepository;
	private final UsuarioService usuarioService;
	private final ActualizaPuntosEventoProducer actualizaPuntosEventoProducer;
	@Value("${server.servlet.context-path}")
	private String context;
	@Value("${server.servlet.context-path-images}")
	private String images;

	@Transactional
	@Synchronized
	public HashMap<String, Object> validaCanje(Long idPuntosRequeridos, Long idUsuario) {
		HashMap<String, Object> variablesDeSalida = new HashMap<String, Object>();
		Long idCategoria = 0L;
		boolean canjeUnico = false;
		boolean usuarioTieneCodigo = false;
		boolean categoriaVigente = false;
		boolean programaVigente = false;
		Timestamp vigenciaInicio;
		Timestamp vigenciaFin;
		int puntosUsuario = 0;
		int puntosCoste = 0;
		int puntosLimiteInferior = 0;
		int puntosLimiteSuperior = 0;

		Optional<UsuarioPuntosColorEntity> usuarioPuntosOpt = this.usuarioPuntosColorEntityRepository.findByIdArqUsuario(idUsuario);
		if(usuarioPuntosOpt.isPresent()) {
			puntosUsuario = usuarioPuntosOpt.get().getPuntos();
		}else {
			throw new IllegalArgumentException("Usuario Incorrecto");
		}

		//Obtenemos programa
		ProgramaProntipagoEntity programaProntipagoEntity = this.programaProntipagoEntityRepository
				.findByPuntosRequeridos(new ProgramaPuntosRequeridosEntity().setIdProgramaPuntosRequeridos(idPuntosRequeridos))
				.orElseThrow(() -> new IllegalArgumentException("Programa Incorrecto"));

		puntosCoste = programaProntipagoEntity.getPuntosRequeridos().getPuntosCoste();
		puntosLimiteInferior = programaProntipagoEntity.getPuntosRequeridos().getPuntosLimiteInferior();
		puntosLimiteSuperior = programaProntipagoEntity.getPuntosRequeridos().getPuntosLimiteSuperior();
		idCategoria = programaProntipagoEntity.getCategoriaProntipago().getIdCategoriaProntipago();
		canjeUnico = programaProntipagoEntity.getCategoriaProntipago().isCanjeUnico();
		vigenciaInicio = programaProntipagoEntity.getCategoriaProntipago().getVigenciaInicio();
		vigenciaFin = programaProntipagoEntity.getCategoriaProntipago().getVigenciaFin();

		//Validamos vigencia
		categoriaVigente = FechasUtil.validaVigencia(vigenciaInicio, vigenciaFin);
		programaVigente = FechasUtil.validaVigencia(programaProntipagoEntity.getVigenciaInicio(),programaProntipagoEntity.getVigenciaFin());

		//Buscamos si el usuario ya tiene un canje de esta categoria
		Optional<ProgramaCodigosDescuentoEntity> codigosDescuentoOpt = programaCodigosDescuentoEntityRepository
				.findByIdArqUsuarioAndIdCategoria(idUsuario, idCategoria);
		if(codigosDescuentoOpt.isPresent()) {
			usuarioTieneCodigo = true;
		}

		variablesDeSalida.put(ConstantesProgramaLealtad.KEY_CATEGORIA_CANJE_UNICO, canjeUnico);
		variablesDeSalida.put(ConstantesProgramaLealtad.KEY_USUARIO_TIENE_CODIGO, usuarioTieneCodigo);
		variablesDeSalida.put(ConstantesProgramaLealtad.KEY_CATEGORIA_VIGENTE, categoriaVigente);
		variablesDeSalida.put(ConstantesProgramaLealtad.KEY_PROGRAMA_VIGENTE, programaVigente);
		variablesDeSalida.put(ConstantesProgramaLealtad.KEY_PUNTOS_USUARIO, puntosUsuario);
		variablesDeSalida.put(ConstantesProgramaLealtad.KEY_PUNTOS_COSTE_SUBPROGRAMA, puntosCoste);
		variablesDeSalida.put(ConstantesProgramaLealtad.KEY_PUNTOS_LIMITE_INFERIOR, puntosLimiteInferior);
		variablesDeSalida.put(ConstantesProgramaLealtad.KEY_PUNTOS_LIMITE_SUPERIOR, puntosLimiteSuperior);

		return variablesDeSalida;
	}
	
	@Transactional
	@Synchronized
	public HashMap<String, Object> descuentoDePuntos(Long idPuntosRequeridos, Long idUsuario) {
		HashMap<String, Object> variablesDeSalida = new HashMap<String, Object>();		
		int puntosCoste = 0;
		int puntosGastados = 0;
		
		ProgramaProntipagoEntity programaProntipagoEntity = null;
				
        //Restamos los puntos canjeados 
        Optional<ProgramaPuntosRequeridosEntity> puntoRequeridosOpt = this.programaPuntosRequeridosEntityRepository.findById(idPuntosRequeridos);
            if(puntoRequeridosOpt.isPresent()) { 
            	programaProntipagoEntity =  puntoRequeridosOpt.get().getProgramaProntipago();
            	puntosCoste = puntoRequeridosOpt.get().getPuntosCoste();
            }else {
            	throw new IllegalArgumentException("No existe en la BD el programa de este IdProgramaPuntosRequeridos: " + idPuntosRequeridos);
            }
            
        Optional<UsuarioPuntosColorEntity> usuarioPuntosOpt = this.usuarioPuntosColorEntityRepository.findByIdArqUsuario(idUsuario);
        UsuarioPuntosColorEntity usuarioPuntos;
            if(usuarioPuntosOpt.isPresent()){
                usuarioPuntos = usuarioPuntosOpt.get();
                puntosGastados = usuarioPuntos.getPuntos() <= puntosCoste ? usuarioPuntos.getPuntos() : puntosCoste;
                usuarioPuntos.setPuntos(usuarioPuntos.getPuntos() - puntosGastados);                
            }else{
                usuarioPuntos = new UsuarioPuntosColorEntity();
                usuarioPuntos.setPuntos(puntosCoste * -1);
                usuarioPuntos.setIdArqUsuario(idUsuario);
                usuarioPuntos.setColorByIdColor(new ColorEntity().setIdColor(1));                
            }
            usuarioPuntos = this.usuarioPuntosColorEntityRepository.save(usuarioPuntos);            	
        	
            //Guardamos la historia del movimiento
            ServicioUsadoEntity servicioUsadoEntity = new ServicioUsadoEntity().setIdProgramaProntipago(
                    programaProntipagoEntity.getIdProgramaProntipago()).setMomento(new Timestamp(System.currentTimeMillis())).setIdArqUsuario(idUsuario);
            this.servicioUsadoEntityRepository.save(servicioUsadoEntity);
            
            //Guardamos el histórico de puntos consumidos
            UsuarioPuntosColorConsumidosEntity usuarioPuntosConsumidos = new UsuarioPuntosColorConsumidosEntity();
            usuarioPuntosConsumidos.setPuntos(puntosCoste);
            usuarioPuntosConsumidos.setColorByIdColor(new ColorEntity().setIdColor(1));
            usuarioPuntosConsumidos.setServicioUsadoByIdServicioUsado(servicioUsadoEntity);
            this.usuarioPuntosColorConsumidosEntityRepository.save(usuarioPuntosConsumidos);
            
            variablesDeSalida.put(ConstantesProgramaLealtad.KEY_ID_CATEGORIA, programaProntipagoEntity.getCategoriaProntipago().getIdCategoriaProntipago());
            variablesDeSalida.put(ConstantesProgramaLealtad.KEY_NOMBRE_CATEGORIA, programaProntipagoEntity.getCategoriaProntipago().getNombre());            
            variablesDeSalida.put(ConstantesProgramaLealtad.KEY_LOGO_CATEGORIA, programaProntipagoEntity.getCategoriaProntipago().getUrlLogo());
            variablesDeSalida.put(ConstantesProgramaLealtad.KEY_LOGO_2_CATEGORIA, programaProntipagoEntity.getCategoriaProntipago().getUrlLogo2());
            variablesDeSalida.put(ConstantesProgramaLealtad.KEY_CATEGORIA_PORTAL, programaProntipagoEntity.getCategoriaProntipago().getUrlPortal());
            variablesDeSalida.put(ConstantesProgramaLealtad.KEY_ID_PROGRAMA, programaProntipagoEntity.getIdProgramaProntipago());

		    actualizaPuntosEventoProducer.send(usuarioPuntos);
            
            return variablesDeSalida;
	}
	
	@Transactional
	@Synchronized
	public HashMap<String, Object> obtencionDeCodigo(Long idUsuario, Long idPuntosRequeridos, boolean canjeUnico) {
		HashMap<String, Object> variablesDeSalida = new HashMap<String, Object>();
		BigDecimal valorDescuentoN = new BigDecimal(0);
		BigDecimal valorDescuentoX = new BigDecimal(0);
		Long idTipoDescuento = 0L;
		String imagenDescuento = "", codigo = "";
		
		//Obtenenemos codigo
    	Optional<ProgramaCodigosDescuentoEntity> codigosDescuentoOpt = programaCodigosDescuentoEntityRepository
    						.findFirstByIdArqUsuarioIsNullAndProgramaPuntosRequeridos(new ProgramaPuntosRequeridosEntity().setIdProgramaPuntosRequeridos(idPuntosRequeridos));
    	
    	if(codigosDescuentoOpt.isPresent()) {
    		codigo = codigosDescuentoOpt.get().getCodigo();
    		idTipoDescuento = codigosDescuentoOpt.get().getProgramaTipoDescuento().getIdProgramaTipoDescuento();
    		imagenDescuento = codigosDescuentoOpt.get().getImagen();
    		valorDescuentoN = codigosDescuentoOpt.get().getValorN();
    		valorDescuentoX = codigosDescuentoOpt.get().getValorX();
    	}
    	
    	//Asignamos codigo al usuario si es canje unico
    	if(canjeUnico) {
	    	codigosDescuentoOpt.get().setIdArqUsuario(idUsuario);
	    	codigosDescuentoOpt.get().setFechaUtilizado(new Timestamp(System.currentTimeMillis()));
	    	programaCodigosDescuentoEntityRepository.save(codigosDescuentoOpt.get());
    	}
    	
    	variablesDeSalida.put(ConstantesProgramaLealtad.KEY_CODIGO_DESCUENTO, codigo);
    	variablesDeSalida.put(ConstantesProgramaLealtad.KEY_TIPO_DESCUENTO, idTipoDescuento);
    	variablesDeSalida.put(ConstantesProgramaLealtad.KEY_IMAGEN_DESCUENTO, imagenDescuento);
    	variablesDeSalida.put(ConstantesProgramaLealtad.KEY_VALOR_DESCUENTO_N, valorDescuentoN);
    	variablesDeSalida.put(ConstantesProgramaLealtad.KEY_VALOR_DESCUENTO_X, valorDescuentoX);
    	
    	return variablesDeSalida;
	}
	
	public void enviarCodigoDescuentoPorMail(Long idUsuario, String plantilla,  
											 String descuento, String codigoDescuento, String imagenDescuento, 
											 String nombreCategoria, String logoCategoria, String urlPortal){
		log.info("enviarCodigoDescuentoPorMail: {}, {}, {}, {}, {}, {}, {}, {}, {}",idUsuario, plantilla, descuento, codigoDescuento, imagenDescuento,  nombreCategoria, logoCategoria, urlPortal);
		
		String baseUrlWithContext = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		String baseUrl = baseUrlWithContext.replace(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath(),"");
		       log.info("baseUrlWithContext: {}",baseUrlWithContext);
		       log.info("imagenDescuento: {}",baseUrl + this.images + imagenDescuento);
		       log.info("logo_biobox: {}",baseUrl + this.images + Constantes.LOGO_BIOBOX_EMAIL);
		       
		String urlCategoria = urlPortal;
		
		String emailUsuario = this.usuarioService.getUsuarioLogeado().getEmail();
				
        var model = new HashMap<String, Object>();        
        model.put("descuento", descuento);
        model.put("codigoDescuento", codigoDescuento);
        model.put("imagenDescuento", baseUrl + this.images + imagenDescuento);
        model.put("nombreCategoria", nombreCategoria);
        model.put("logoCategoria", logoCategoria);
        model.put("logoBioBox", baseUrl + this.images + Constantes.LOGO_BIOBOX_EMAIL);
        model.put("urlCategoria", urlCategoria);
        model.put("baseUrl",baseUrl);
        
        var mailDTO = new MailDTO();
        mailDTO.setMailTo(emailUsuario);
        mailDTO.setMailSubject("BioBox - Código de descuento");
        mailDTO.setModel(model);
        
        try{
            this.emailOperationsThymeleafService.sendEmail(mailDTO, plantilla);
        } catch (Exception ex) {
            log.error("Ocurrió un error al enviar el email de código de descuento: {}", ex.getMessage());
        }
        
    }
	
}
