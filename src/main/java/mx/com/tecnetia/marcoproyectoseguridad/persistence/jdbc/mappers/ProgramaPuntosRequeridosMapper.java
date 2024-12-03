package mx.com.tecnetia.marcoproyectoseguridad.persistence.jdbc.mappers;

import java.util.ArrayList;
import java.util.List;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosRequeridosDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaPuntosRequeridosEntity;

public class ProgramaPuntosRequeridosMapper {

	public static PuntosRequeridosDTO entityToDto(ProgramaPuntosRequeridosEntity entity) {
		PuntosRequeridosDTO dto = null;
		if(entity!=null) {
			dto = new PuntosRequeridosDTO();
			dto.setIdPuntosRequeridos(entity.getIdProgramaPuntosRequeridos());
			dto.setIdPrograma(entity.getProgramaProntipago().getIdProgramaProntipago());
			dto.setPuntosLimiteInferior(entity.getPuntosLimiteInferior());
			dto.setPuntosLimiteSuperior(entity.getPuntosLimiteSuperior());
			dto.setPuntosCoste(entity.getPuntosCoste());
		}
		return dto;
	}
	
	public static List<PuntosRequeridosDTO> entityToDtoList(List<ProgramaPuntosRequeridosEntity> entityList) {
		List<PuntosRequeridosDTO> dtoList = new ArrayList<PuntosRequeridosDTO>();
		if(entityList!=null) {
			for (ProgramaPuntosRequeridosEntity entity : entityList) {
				dtoList.add(entityToDto(entity));
			}
		}
		return dtoList;
	}
}
