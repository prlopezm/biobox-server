package mx.com.tecnetia.marcoproyectoseguridad.mapper;

import mx.com.tecnetia.marcoproyectoseguridad.dto.metro.QrMetroDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.QrMetroGeneradoEntity;
import org.mapstruct.Mapper;

@Mapper
public interface QrMetroGeneradoMapper {
    QrMetroGeneradoEntity toEntity(QrMetroDTO dto);
}
