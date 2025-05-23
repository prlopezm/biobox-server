package mx.com.tecnetia.orthogonal.ampq.mapper;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.QuioscoEntity;
import mx.com.tecnetia.orthogonal.ampq.dto.QuioscoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuioscoMapper {

    QuioscoDTO fromEntity(QuioscoEntity quioscoEntity);
}
