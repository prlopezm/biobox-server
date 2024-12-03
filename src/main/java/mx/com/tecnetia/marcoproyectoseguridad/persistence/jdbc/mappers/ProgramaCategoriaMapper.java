package mx.com.tecnetia.marcoproyectoseguridad.persistence.jdbc.mappers;

import java.util.ArrayList;
import java.util.List;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.ProgramaCategoriaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaCategoriaProntipagoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.util.FechasUtil;

public class ProgramaCategoriaMapper {

	
	public static ProgramaCategoriaDTO entityToDto(ProgramaCategoriaProntipagoEntity entity) {
		ProgramaCategoriaDTO dto = null;
		if(entity!=null) {
			dto = new ProgramaCategoriaDTO();
			dto.setColorFondo(entity.getColorFondo());
			dto.setDescripcion(entity.getDescripcion());
			dto.setIdCategoriaPrograma(entity.getIdCategoriaProntipago());
			dto.setNombre(entity.getNombre());
			dto.setPuntosRequeridos(new ArrayList<PuntosColorDTO>());
			dto.setSubprogramas(ProgramaSubProgramaMapper.entityToDtoList(entity.getProgramasProntipagos()));
			dto.setTipoPrograma(entity.getTipoPrograma());
			dto.setUrlLogo(entity.getUrlLogo());
			dto.setUrlLogo2(entity.getUrlLogo2());
			dto.setUrlPortal(entity.getUrlPortal());
			dto.setCanjeUnico(entity.isCanjeUnico());
			dto.setVigenciaFin(entity.getVigenciaFin());
			dto.setVigenciaInicio(entity.getVigenciaInicio());
			dto.setVigente(FechasUtil.validaVigencia(entity.getVigenciaInicio(), entity.getVigenciaFin()));
		}	
		return dto;
	}
	
	public static List<ProgramaCategoriaDTO> entityToDtoList(List<ProgramaCategoriaProntipagoEntity> entityList) {
		List<ProgramaCategoriaDTO> dtoList = new ArrayList<ProgramaCategoriaDTO>();		
		if(entityList!=null) {
			for (ProgramaCategoriaProntipagoEntity entity : entityList) {
				dtoList.add(entityToDto(entity));
			}		
		}
		return dtoList;
	}
	
	
}
