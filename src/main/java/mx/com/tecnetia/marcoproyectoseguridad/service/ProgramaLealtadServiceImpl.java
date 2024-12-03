package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.MovimientosDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.ProgramaCategoriaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.ProgramaSubprogramaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosRequeridosValidadosDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosUsuarioDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaCategoriaProntipagoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ProgramaCategoriaProntipagoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorAcumuladoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorConsumidosEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.template.UsuarioPuntosColorJdbcRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.jdbc.mappers.ProgramaCategoriaMapper;
import mx.com.tecnetia.marcoproyectoseguridad.util.TipoProgramaEnum;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqPropiedadEntityRepository;

@Service
@RequiredArgsConstructor
public class ProgramaLealtadServiceImpl implements ProgramaLealtadService {
	
    private final UsuarioPuntosEntityRepository usuarioPuntosEntityRepository;
    private final UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;
    private final UsuarioPuntosColorJdbcRepository usuarioPuntosColorJdbcRepository;
    private final ProgramaCategoriaProntipagoEntityRepository categoriaProntipagoEntityRepository;
    private final ArqPropiedadEntityRepository arqPropiedadEntityRepository;
    private final Environment environment;
    private final UsuarioPuntosColorAcumuladoEntityRepository usuarioPuntosColorAcumuladoEntityRepository;
    private final UsuarioPuntosColorConsumidosEntityRepository usuarioPuntosColorConsumidosEntityRepository;
    private final ProntiPagoService prontiPagoService;
    
    @Override
    @Transactional(readOnly = true)
    public PuntosUsuarioDTO getPuntosUsuario(Long idArqUsuario) {
        PuntosUsuarioDTO puntosDTO = new PuntosUsuarioDTO();
        puntosDTO = this.usuarioPuntosEntityRepository.getDTOByUsuario(idArqUsuario);
        return puntosDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PuntosColorDTO> getPuntosUsuarioXColor(Long idArqUsuario) {
        return this.usuarioPuntosColorEntityRepository.getDTOByUsuario(idArqUsuario);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MovimientosDTO> getUltimosMovimientosDeUsuario(Long idArqUsuario) {
        List<MovimientosDTO> movimientos = this.usuarioPuntosColorJdbcRepository.getMovimientosDeUsuario(idArqUsuario);
        for (MovimientosDTO movimiento : movimientos) {
            // Si es un reciclaje (suma en el movimiento)
            List<PuntosColorDTO> puntos = null;
            if(movimiento.getTipoMovimiento().equals(1)){
                puntos = this.usuarioPuntosColorAcumuladoEntityRepository.getPuntosByReciclaje(movimiento.getIdProductoReciclado());
                movimiento.setPuntos(puntos);
            }else{
                // Si es un canje (resta en el movimiento)
                if(movimiento.getTipoMovimiento().equals(2)) {
                    puntos = this.usuarioPuntosColorConsumidosEntityRepository.getPuntosByCanje(movimiento.getIdProductoReciclado());
                    movimiento.setPuntos(puntos);
                }
            }
        }
        return movimientos;
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unused")
    public List<ProgramaCategoriaDTO> getProgramasLealtad(Long idArqUsuario) {    	
    	
        String precioMaximoCve = environment.getProperty("prontipagos.precio.max");
        
        BigDecimal precioMax = new BigDecimal(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(precioMaximoCve)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + precioMaximoCve)).getValor());

        Optional<UsuarioPuntosColorEntity> usuarioPuntosOpt = this.usuarioPuntosColorEntityRepository.findByIdArqUsuario(idArqUsuario);        
        Timestamp fechaActual = Timestamp.from(Instant.now());
        List<ProgramaCategoriaProntipagoEntity> categorias = this.categoriaProntipagoEntityRepository.findByTipoProgramaIsNotAndIsValid(TipoProgramaEnum.PRONTIPAGO.getTipoPrograma(),fechaActual);        
        List<ProgramaCategoriaDTO> categoriasDTO = ProgramaCategoriaMapper.entityToDtoList(categorias);
        
        //Descartamos subprogramas no vigentes
        categoriasDTO.forEach(c -> {
        	c.getSubprogramas().removeIf(p -> !p.isVigente());
        });
        
        categoriasDTO.forEach(categoria ->{        	
        	categoria.getSubprogramas().forEach(subprograma ->{
        		PuntosRequeridosValidadosDTO puntos = null;
        		if(subprograma.getProgramaPuntosRequeridos()!=null) {
        			puntos = new PuntosRequeridosValidadosDTO();
	        		puntos.setIdPuntosRequeridos(subprograma.getProgramaPuntosRequeridos().getIdPuntosRequeridos());
	        		puntos.setPuntos(subprograma.getProgramaPuntosRequeridos().getPuntosCoste());
	        		
	        		//Validamos puntaje de la categoria y del usuario
	        		int puntosusuario = usuarioPuntosOpt.get().getPuntos();
	        		int puntosLimiteInferior = subprograma.getProgramaPuntosRequeridos().getPuntosLimiteInferior();
	        		int puntosLimiteSuperiror = subprograma.getProgramaPuntosRequeridos().getPuntosLimiteInferior();
	        		//puntos.setHabilitado((puntosusuario >= puntosLimiteInferior && puntosusuario <= puntosLimiteSuperiror) ? true :  false);
                    puntos.setHabilitado(puntosusuario >= puntosLimiteInferior);
	        		//Usando temporalmente el obtejo de puntoColor
	        		if(subprograma.getPuntosRequeridos().isEmpty()) {
	        			subprograma.getPuntosRequeridos().add(new PuntosColorDTO());
	        		}
	        		subprograma.getPuntosRequeridos().get(0).setIdPuntosRequeridos(puntos.getIdPuntosRequeridos());
	        		subprograma.getPuntosRequeridos().get(0).setPuntos(puntos.getPuntos());
	        		subprograma.getPuntosRequeridos().get(0).setHabilitado(puntos.isHabilitado());	        		
	        		//termina usando temporalmente el obtejo de puntoColor
        		}
        		subprograma.setPuntos(puntos);
        	});
        });
       
        //Obtenemos las categorias de prontipagos
        categoriasDTO.addAll(this.prontiPagoService.getProgramasProntipagos(idArqUsuario, precioMax));
        
        //Ordenamos las categorias y los subprogramas
        categoriasDTO = categoriasDTO.stream().sorted(Comparator.comparing(ProgramaCategoriaDTO::getNombre)).collect(Collectors.toList());
        categoriasDTO.forEach(categoria->{
        	categoria.setSubprogramas(
        				categoria.getSubprogramas().stream()
        				.sorted(Comparator.comparing(ProgramaSubprogramaDTO::getSku))
        				.collect(Collectors.toList())
            );
        });

        return categoriasDTO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProgramaCategoriaDTO> getProgramasLealtadFake() {
        List<ProgramaCategoriaDTO> programasLealtadTmpList = new ArrayList<ProgramaCategoriaDTO>();

        ProgramaCategoriaDTO puntosProgramaLealtadDTO =
                new ProgramaCategoriaDTO(
                        11L,
                        "Premios Varios",
                        "Canjea por diversos premios",
                        "#000000",
                        "p",
                         "https://tecnetiadev.com/pics/premio.png");
        List<PuntosColorDTO> puntosList = new ArrayList<PuntosColorDTO>();
        PuntosColorDTO puntos = new PuntosColorDTO();
        puntos.setPuntos(4);
        puntos.setColor("#2BABE2");
        puntos.setIdColor(1);
        puntos.setUrlFoto("https://tecnetiadev.com/pics/colorAzul.png");
        puntos.setNombreColor("BioBox");
        puntosList.add(puntos);

        puntosProgramaLealtadDTO.setPuntosRequeridos(puntosList);
        List<ProgramaSubprogramaDTO> subprogramas = new ArrayList<ProgramaSubprogramaDTO>();
        ProgramaSubprogramaDTO subprograma = new ProgramaSubprogramaDTO(
                40L, "PREMIOSFK1","Frasco de Chocolates",
                "Frasco de chocolates varios a precio especial", new BigDecimal(20), puntosList);
        subprogramas.add(subprograma);

        puntosList = new ArrayList<PuntosColorDTO>();
        puntos = new PuntosColorDTO();
        puntos.setPuntos(6);
        puntos.setColor("#2BABE2");
        puntos.setIdColor(1);
        puntos.setUrlFoto("https://tecnetiadev.com/pics/colorAzul.png");
        puntos.setNombreColor("BioBox");
        puntosList.add(puntos);

        subprograma = new ProgramaSubprogramaDTO(
                41L, "PREMIOSFK2","Canasta de fruta",
                "Canasta de fruta decorada a precio especial", new BigDecimal(30), puntosList);
        subprogramas.add(subprograma);

        puntosList = new ArrayList<PuntosColorDTO>();
        puntos = new PuntosColorDTO();
        puntos.setPuntos(10);
        puntos.setColor("#2BABE2");
        puntos.setIdColor(1);
        puntos.setUrlFoto("https://tecnetiadev.com/pics/colorAzul.png");
        puntos.setNombreColor("BioBox");
        puntosList.add(puntos);

        subprograma = new ProgramaSubprogramaDTO(
                42L, "PREMIOSFK3","Set de Vinos",
                "Tres vinos tintos a precio especial", new BigDecimal(100), puntosList);
        subprogramas.add(subprograma);

        puntosList = new ArrayList<PuntosColorDTO>();
        puntos = new PuntosColorDTO();
        puntos.setPuntos(30);
        puntos.setColor("#2BABE2");
        puntos.setIdColor(1);
        puntos.setUrlFoto("https://tecnetiadev.com/pics/colorAzul.png");
        puntos.setNombreColor("BioBox");
        puntosList.add(puntos);

        subprograma = new ProgramaSubprogramaDTO(
                43L, "PREMIOSFK4","Juego de Video",
                "Juego de video con tres juegos a precio especial", new BigDecimal(1000), puntosList);
        subprogramas.add(subprograma);

        puntosProgramaLealtadDTO.setSubprogramas(subprogramas);
        programasLealtadTmpList.add(puntosProgramaLealtadDTO);

        return programasLealtadTmpList;
       }

}
