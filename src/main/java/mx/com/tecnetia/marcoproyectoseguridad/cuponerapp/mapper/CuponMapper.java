package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.mapper;

import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CuponMapper {
    CuponDTO fromEntity(CuponerappEntity cuponerappEntity);
}
