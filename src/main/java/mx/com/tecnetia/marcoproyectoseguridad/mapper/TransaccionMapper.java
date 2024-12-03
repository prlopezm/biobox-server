package mx.com.tecnetia.marcoproyectoseguridad.mapper;

import mx.com.tecnetia.marcoproyectoseguridad.dto.metro.AccesoMetroArchivoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.TransaccionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TransaccionMapper {
    TransaccionEntity toEntity(AccesoMetroArchivoDTO dto);
}
