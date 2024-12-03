package mx.com.tecnetia.marcoproyectoseguridad.persistence.jdbc.mappers;

import java.util.ArrayList;
import java.util.List;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ServicioEntity;

public class ProgramaPuntosColorMapper {

	
	public static PuntosColorDTO entityToDto(ServicioEntity entity) {
		PuntosColorDTO dto = null;
		if(entity!=null) {
			dto = new PuntosColorDTO();
			dto.setColor(entity.getColorByIdColor().getHexadecimal());
			dto.setNombreColor(entity.getColorByIdColor().getNombre());
			dto.setIdColor(entity.getColorByIdColor().getIdColor());
			dto.setPuntos(entity.getPuntos());
			dto.setUrlFoto(entity.getColorByIdColor().getUrlFoto());
			dto.setUrlFoto2(entity.getColorByIdColor().getUrlFoto2());
		}
		return dto;		
	}
	
	public static List<PuntosColorDTO> entityToDtoList(List<ServicioEntity> entityList) {
		List<PuntosColorDTO> dtoList = new ArrayList<PuntosColorDTO>();
		if(entityList!=null) {
			for (ServicioEntity entity : entityList) {
				dtoList.add(entityToDto(entity));
			}
		}	
		return dtoList;
	}
}
