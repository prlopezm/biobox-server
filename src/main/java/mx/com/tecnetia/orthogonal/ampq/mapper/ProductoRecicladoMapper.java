package mx.com.tecnetia.orthogonal.ampq.mapper;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProductoRecicladoEntity;
import mx.com.tecnetia.orthogonal.ampq.dto.ProductoRecicladoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoRecicladoMapper {

    ProductoRecicladoDTO fromEntity(ProductoRecicladoEntity productoRecicladoEntity);
}
