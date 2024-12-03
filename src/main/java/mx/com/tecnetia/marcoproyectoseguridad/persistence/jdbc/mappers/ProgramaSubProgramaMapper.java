package mx.com.tecnetia.marcoproyectoseguridad.persistence.jdbc.mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.ProgramaSubprogramaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaProntipagoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.util.FechasUtil;

public class ProgramaSubProgramaMapper {

	public static ProgramaSubprogramaDTO entityToDto(ProgramaProntipagoEntity entity) {
		ProgramaSubprogramaDTO dto = null;
		if(entity!=null) {
			dto = new ProgramaSubprogramaDTO();
			dto.setDescripcion(entity.getDescripcion());
			dto.setIdPrograma(entity.getIdProgramaProntipago());
			dto.setSku(entity.getCodigo());
			dto.setMonto(new BigDecimal(0.0));
			dto.setNombre(entity.getNombre());
			dto.setVigenciaFin(entity.getVigenciaFin());
			dto.setVigenciaInicio(entity.getVigenciaInicio());
			dto.setVigente(FechasUtil.validaVigencia(entity.getVigenciaInicio(), entity.getVigenciaFin()));
			dto.setPuntosRequeridos(ProgramaPuntosColorMapper.entityToDtoList(entity.getPuntosColor()));
			dto.setProgramaPuntosRequeridos(ProgramaPuntosRequeridosMapper.entityToDto(entity.getPuntosRequeridos()));
			dto.setPuntos(null);
		}
		return dto;
	}
	
	public static List<ProgramaSubprogramaDTO> entityToDtoList(List<ProgramaProntipagoEntity> entityList) {
		List<ProgramaSubprogramaDTO> dtoList = new ArrayList<ProgramaSubprogramaDTO>();
		if(entityList!=null) {
			for (ProgramaProntipagoEntity entity : entityList) {
				dtoList.add(entityToDto(entity));
			}
		}	
		return dtoList;
	}
}
