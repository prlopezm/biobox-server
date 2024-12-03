package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.ws.BindingProvider;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.client.ProductWsTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.client.ProntipagosTopUpServiceEndPoint;
import mx.com.tecnetia.marcoproyectoseguridad.dto.client.ProntipagosTopUpServiceEndPoint_Service;
import mx.com.tecnetia.marcoproyectoseguridad.dto.client.ResponseCatalogProductTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.client.TransactionResponseDto;
import mx.com.tecnetia.marcoproyectoseguridad.dto.client.TransactionResponseTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.ProgramaCategoriaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.ProgramaSubprogramaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorTmpDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosRequeridosValidadosDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ColorEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ConversionEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaCategoriaProntipagoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaProntipagoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.RespuestaProntipagoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ServicioUsadoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorConsumidosEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ColorEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ConversionEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ProgramaCategoriaProntipagoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ProgramaProntipagoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.RespuestaProntipagoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ServicioUsadoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorConsumidosEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.jdbc.mappers.ProgramaCategoriaMapper;
import mx.com.tecnetia.marcoproyectoseguridad.util.CodigoRespuestaProntipagosEnum;
import mx.com.tecnetia.marcoproyectoseguridad.util.FechasUtil;
import mx.com.tecnetia.marcoproyectoseguridad.util.TipoProgramaEnum;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqPropiedadEntityRepository;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqUsuarioRepository;

@Service
@RequiredArgsConstructor
public class ProntiPagoServiceImpl implements ProntiPagoService {
    private final RespuestaProntipagoEntityRepository respuestaProntipagoEntityRepository;
    private final UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;
    private final ProgramaProntipagoEntityRepository programaProntipagoEntityRepository;
    private final ArqPropiedadEntityRepository arqPropiedadEntityRepository;
    private final ArqUsuarioRepository arqUsuarioRepository;
    private final ConversionEntityRepository conversionEntityRepository;
    private final Environment environment;
    private final ProntipagosTopUpServiceEndPoint_Service prontipagosTopUpServiceEndPoint_service;
    private final ServicioUsadoEntityRepository servicioUsadoEntityRepository;
    private final UsuarioPuntosColorConsumidosEntityRepository usuarioPuntosColorConsumidosEntityRepository;
    private final ColorEntityRepository colorEntityRepository;
    private final ProgramaCategoriaProntipagoEntityRepository categoriaProntipagoEntityRepository;

    @Override
    @Transactional(readOnly = false)
    public List<ProgramaCategoriaDTO> getProgramasProntipagos(Long idArqUsuario, BigDecimal precioMax) {
    	
    	List<ProgramaCategoriaDTO> categoriasList = new ArrayList<ProgramaCategoriaDTO>(); 
    	Timestamp fechaActual = Timestamp.from(Instant.now());
        List<ProgramaCategoriaProntipagoEntity> categorias = this.categoriaProntipagoEntityRepository.findByTipoProgramaAndIsValid(TipoProgramaEnum.PRONTIPAGO.getTipoPrograma(), fechaActual);
        List<ProgramaCategoriaDTO> categoriasDTO = ProgramaCategoriaMapper.entityToDtoList(categorias);
        Optional<UsuarioPuntosColorEntity> usuarioPuntosOpt = this.usuarioPuntosColorEntityRepository.findByIdArqUsuario(idArqUsuario);
        Optional<ConversionEntity> conversion =  this.conversionEntityRepository.getByFechaFinIsNull();
        ResponseCatalogProductTO productosResponse = this.getProgramasProntipagoWS();
        List<PuntosColorTmpDTO> puntosConversion = this.conversionEntityRepository.getActiveDTOByPrice();                    
        
	        //Descartamos subprogramas no vigentes
	        categoriasDTO.forEach(c -> {
	        	c.getSubprogramas().removeIf(p -> !p.isVigente());
	        });
        
            if(productosResponse.getErrorResponseTO()!=null){
                if(productosResponse.getErrorResponseTO().getErrorCode().equals("00")){
                        	
                        	categoriasDTO.forEach(categoria ->{
                        		
                        		List<ProgramaSubprogramaDTO> subProgramaList = new ArrayList<ProgramaSubprogramaDTO>();
                        		
                            	categoria.getSubprogramas().forEach(subprograma ->{
                            		
                            		Optional<ProductWsTO> productoOpt = productosResponse.getProducts().stream().filter(x -> x.getSku().equals(subprograma.getSku())).findAny();
                            		if(productoOpt.isPresent()) {
                            		
	                            		if(productoOpt.get().getPrice().compareTo(precioMax)<=0){
	                            			//Asignamos puntos requeridos por color
	                            			int puntosRequeridos = new BigDecimal(conversion.get().getMontoCentavos())
	                            																  .divide(new BigDecimal(100))
	                            																  .multiply(new BigDecimal(conversion.get().getPuntos()))
	                            																  .multiply(productoOpt.get().getPrice())
	                            																  .intValue();
	                            			
	                            			//Asignamos puntos requeridos
	                                		PuntosRequeridosValidadosDTO puntos = new PuntosRequeridosValidadosDTO();
	                                		puntos.setIdPuntosRequeridos(subprograma.getPuntos() == null ? 0L : subprograma.getPuntos().getIdPuntosRequeridos());
	                                		puntos.setPuntos(puntosRequeridos);	                                			                    	        		
	                    	        		puntos.setHabilitado(usuarioPuntosOpt.get().getPuntos() >= puntosRequeridos ? true : false); //Validamos puntaje de la categoria y del usuario
	                    	        		subprograma.setPuntos(puntos);
	                    	        		
	                    	        		//Asignamos puntos por color   
	                    	        		PuntosColorDTO puntosColor = new PuntosColorDTO(puntos.getIdPuntosRequeridos(),
								                    	        						    puntos.isHabilitado(),
								                    	        						    puntosConversion.get(0).getIdColor(),
								                    	        			        	    puntosConversion.get(0).getNombreColor(),
								                    	        			        	    puntosConversion.get(0).getColor(),
								                    	        			        	    puntosRequeridos,
								                    	        			        	    puntosConversion.get(0).getUrlFoto(),
								                    	        			        	    puntosConversion.get(0).getUrlFoto2()
	                    	        			        	   							   );
	                            			subprograma.getPuntosRequeridos().add(puntosColor);
	                            			subprograma.setNombre(productoOpt.get().getProductName());
	                            			subprograma.setDescripcion(productoOpt.get().getDescription());
	                            			subprograma.setMonto(productoOpt.get().getPrice());
	                            			
	                            			//Agregamos subprograma
	                            			subProgramaList.add(subprograma);	                            			
	                            		}                             		    
                            		}                            		                          		  
                            		
                            	});
                            	//Descartamos protipagos que no esten en el catalogo local
                            	if(!subProgramaList.isEmpty()) {
	                            	categoria.setSubprogramas(subProgramaList);
	                            	categoriasList.add(categoria); 
                            	}
                            	
                            }); 
                }
            }
    	return categoriasList;
    }

    @Override
    @Transactional(readOnly = false)
    public MensajeDTO<?> canjearProntipago(String sku, BigDecimal amount, Long idUsuario){
    	int puntosUsuario = 0;
		int puntosCoste = 0;
		boolean categoriaVigente = false;
		boolean programaVigente = false;
		
        String telefonoUsuario = this.arqUsuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD el usuario: " + idUsuario)).getTelefono();
        ProgramaProntipagoEntity programaProntipagoEntity = this.programaProntipagoEntityRepository.findByCodigo(sku)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD el programa prontipago de ese sku: " + sku));

        //Obtenemos los puntos del usuario
        Optional<UsuarioPuntosColorEntity> usuarioPuntosOpt = this.usuarioPuntosColorEntityRepository.findByIdArqUsuario(idUsuario);
		if(usuarioPuntosOpt.isPresent()) {
			puntosUsuario = usuarioPuntosOpt.get().getPuntos(); 
		}else {
			throw new IllegalArgumentException("Usuario Incorrecto");
		}
		
		//Obtenemos los puntos requeridos para el subprograma
        List<PuntosColorTmpDTO> puntosXColorTmp = this.conversionEntityRepository.getActiveDTOByPrice();
        List<PuntosColorDTO> puntosXColor = puntosXColorTmp.stream().map(v -> 
        										new PuntosColorDTO(v.getIdColor(),
													        		v.getNombreColor(),
													        		v.getColor(),
													        		(new BigDecimal(v.getMontoCentavos()).divide(new BigDecimal(100))).multiply(new BigDecimal(v.getPuntos())).multiply(amount).intValue(),
													        		v.getUrlFoto(),
													        		v.getUrlFoto2()))
													                .toList();        
        puntosCoste = puntosXColor.get(0).getPuntos();
        categoriaVigente = FechasUtil.validaVigencia(programaProntipagoEntity.getCategoriaProntipago().getVigenciaInicio(), programaProntipagoEntity.getCategoriaProntipago().getVigenciaFin());
        programaVigente = FechasUtil.validaVigencia(programaProntipagoEntity.getVigenciaInicio(), programaProntipagoEntity.getVigenciaFin());
        
        if(!categoriaVigente || !programaVigente) {
        	return new MensajeDTO<>("El programa no se encuentra vigente.");
        }
        if(puntosUsuario < puntosCoste) {
        	return new MensajeDTO<>("No cuentas con puntos suficientes.");
        }                
        
		TransactionResponseDto transaction = this.enviarCanjeProntipago(amount.doubleValue(),telefonoUsuario,sku);
		TransactionResponseTO estatus = new TransactionResponseTO();
		estatus.setCodeTransaction(CodigoRespuestaProntipagosEnum.PROCESANDO_TRANSACCION.getCodigoRespuesta());
		int i = 1;
		while(i<30 && (estatus.getCodeTransaction().equals(CodigoRespuestaProntipagosEnum.PROCESANDO_TRANSACCION.getCodigoRespuesta()) 
		        		   || estatus.getCodeTransaction().equals(CodigoRespuestaProntipagosEnum.NO_APLICA.getCodigoRespuesta()))) {
				try {
				      Thread.sleep(2000);
				      estatus = this.getEstatusProntipago(transaction.getTransactionId());
				      i = i + 1;
				}catch (InterruptedException e) {
					e.printStackTrace();
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		
        RespuestaProntipagoEntity respuestaProntipagoEntity = new RespuestaProntipagoEntity();
		respuestaProntipagoEntity.setCodeDescription(estatus.getCodeDescription());
		respuestaProntipagoEntity.setAdditionalInfo(estatus.getAdditionalInfo());
		respuestaProntipagoEntity.setCodeTransaction(estatus.getCodeTransaction());
		respuestaProntipagoEntity.setStatusTransaction(estatus.getStatusTransaction());
		respuestaProntipagoEntity.setDateTransaction(estatus.getDateTransaction());
		respuestaProntipagoEntity.setFolioTransaction(estatus.getFolioTransaction());
		respuestaProntipagoEntity.setTransactionId(estatus.getTransactionId());
		respuestaProntipagoEntity.setTransactionStatusDescription(estatus.getTransactionStatusDescription());
		respuestaProntipagoEntity.setIdProgramaProntipago(programaProntipagoEntity.getIdProgramaProntipago());
		respuestaProntipagoEntity.setIdArqUsuario(idUsuario);		
		
		   if(estatus.getCodeTransaction().equals(CodigoRespuestaProntipagosEnum.TRANSACCION_EXITOSA.getCodigoRespuesta())){
		            //guardamos la historia del movimiento
		            ServicioUsadoEntity servicioUsadoEntity = new ServicioUsadoEntity().setIdProgramaProntipago(
		                    programaProntipagoEntity.getIdProgramaProntipago()).setMomento(new Timestamp(System.currentTimeMillis())).setIdArqUsuario(idUsuario);
		            this.servicioUsadoEntityRepository.save(servicioUsadoEntity);
		            respuestaProntipagoEntity.setIdServicioUsado(servicioUsadoEntity.getIdServicioUsado());
		
		            //Restamos los puntos canjeados
		            for(PuntosColorDTO color : puntosXColor){		                
		                Optional<ColorEntity> colorEntity = this.colorEntityRepository.findById(color.getIdColor());
		
		                if(usuarioPuntosOpt.isPresent()){
		                    UsuarioPuntosColorEntity usuarioPuntos = usuarioPuntosOpt.get();
		                    usuarioPuntos.setPuntos(usuarioPuntos.getPuntos() - color.getPuntos().intValue());
		                    this.usuarioPuntosColorEntityRepository.save(usuarioPuntos);
		                }else{
		                    UsuarioPuntosColorEntity usuarioPuntos = new UsuarioPuntosColorEntity();
		                    usuarioPuntos.setPuntos(color.getPuntos().intValue() * -1);
		                    usuarioPuntos.setIdArqUsuario(idUsuario);
		                    usuarioPuntos.setColorByIdColor(colorEntity.get());
		                    usuarioPuntos = this.usuarioPuntosColorEntityRepository.save(usuarioPuntos);
		                }
		
		                //guardamos el histórico de movimientos
		                UsuarioPuntosColorConsumidosEntity usuarioPuntosConsumidos = new UsuarioPuntosColorConsumidosEntity();
		                usuarioPuntosConsumidos.setPuntos(color.getPuntos().intValue());
		                usuarioPuntosConsumidos.setColorByIdColor(colorEntity.get());
		                usuarioPuntosConsumidos.setServicioUsadoByIdServicioUsado(servicioUsadoEntity);
		                this.usuarioPuntosColorConsumidosEntityRepository.save(usuarioPuntosConsumidos);
		            }
		            this.respuestaProntipagoEntityRepository.save(respuestaProntipagoEntity);
		
		            return new MensajeDTO<>("Tus puntos se han canjeado y en breve recibirás un SMS con la información para hacer válido tu beneficio.");
		   }else {
		
		            if(estatus.getCodeTransaction().equals(CodigoRespuestaProntipagosEnum.NO_APLICA.getCodigoRespuesta())
		                || estatus.getCodeTransaction().equals(CodigoRespuestaProntipagosEnum.PROCESANDO_TRANSACCION.getCodigoRespuesta())){
		                estatus.setCodeDescription("Tiempo de espera agotado, intenta más tarde.");
		            }
		            respuestaProntipagoEntity.setIdServicioUsado(null);
		            this.respuestaProntipagoEntityRepository.save(respuestaProntipagoEntity);
		
		            return new MensajeDTO<>("No se pudo realizar el canje de tus puntos. "+estatus.getCodeDescription());
		   }

    }
    
    @Override
    @Transactional(readOnly = true)
    public TransactionResponseDto enviarCanjeProntipago(Double amount, String clientReference, String sku){
        String usrCve = this.environment.getProperty("prontipagos.services.user");
        String usuario = this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(usrCve)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + usrCve)).getValor();
        String pwdCve = this.environment.getProperty("prontipagos.services.pwd");
        String pwd = this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(pwdCve)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + pwdCve)).getValor();

        ProntipagosTopUpServiceEndPoint port = this.prontipagosTopUpServiceEndPoint_service.getProntipagosTopUpServiceEndPointPort();
        BindingProvider prov = (BindingProvider) port;

        prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, usuario);
        prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, pwd);

        TransactionResponseDto response = port.sellService(amount,clientReference,sku, "");

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseCatalogProductTO getProgramasProntipagoWS(){
        ResponseCatalogProductTO response = null;
        try {
            String usrCve = this.environment.getProperty("prontipagos.services.user");
            String usuario = this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(usrCve)
                    .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + usrCve)).getValor();
            String pwdCve = this.environment.getProperty("prontipagos.services.pwd");
            String pwd = this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(pwdCve)
                    .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + pwdCve)).getValor();

            ProntipagosTopUpServiceEndPoint port = this.prontipagosTopUpServiceEndPoint_service.getProntipagosTopUpServiceEndPointPort();
            BindingProvider prov = (BindingProvider) port;

            prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, usuario);
            prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, pwd);

             response = port.obtainCatalogProducts();
        }catch(Exception e){
            throw new IllegalArgumentException("No se pudieron obtener los beneficios. Intenta más tarde.");
        }

        return response;
    }
    
    @Override
    @Transactional(readOnly = true)
    public TransactionResponseTO getEstatusProntipago(String transactionId){
        String usrCve = this.environment.getProperty("prontipagos.services.user");
        String usuario = this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(usrCve)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + usrCve)).getValor();
        String pwdCve = this.environment.getProperty("prontipagos.services.pwd");
        String pwd = this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(pwdCve)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + pwdCve)).getValor();

        ProntipagosTopUpServiceEndPoint port = this.prontipagosTopUpServiceEndPoint_service.getProntipagosTopUpServiceEndPointPort();
        BindingProvider prov = (BindingProvider) port;

        prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, usuario);
        prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, pwd);

        TransactionResponseTO response = port.checkStatusService(transactionId, null);

        return response;
    }
    
}
