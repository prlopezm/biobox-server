package mx.com.tecnetia.marcoproyectoseguridad.oxxo.mapper;

import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.OpcionCanjeOxxoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.OpcionCanjeOxxoEntityDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity.OpcionCanjeOxxoEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OpcionCanjeOxxoEntityMapper {
    @Mapping(target = "puntosCanjear", ignore = true)
    OpcionCanjeOxxoEntity toEntity(OpcionCanjeOxxoDTO opcionCanjeOxxoEntityDTO);

    OpcionCanjeOxxoDTO toDto(OpcionCanjeOxxoEntity opcionCanjeOxxoEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OpcionCanjeOxxoEntity partialUpdate(OpcionCanjeOxxoDTO opcionCanjeOxxoDTO, @MappingTarget OpcionCanjeOxxoEntity opcionCanjeOxxoEntity);
}
