package mx.com.tecnetia.orthogonal.ampq.mapper;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorAcumuladoEntity;
import mx.com.tecnetia.orthogonal.ampq.dto.UsuarioPuntosColorAcumuladosDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioPuntosColorAcumuladoMapper {

    UsuarioPuntosColorAcumuladosDTO fromEntity(UsuarioPuntosColorAcumuladoEntity usuarioPuntosColorAcumuladoEntity);
}
