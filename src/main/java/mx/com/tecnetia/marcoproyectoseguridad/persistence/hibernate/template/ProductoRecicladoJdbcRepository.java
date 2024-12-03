package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.template;

import java.util.List;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.DiaReciclajeBloqueadoDTO;

public interface ProductoRecicladoJdbcRepository {
        List<DiaReciclajeBloqueadoDTO> getDiasReciclajesBloaqueados(Long idUsuario, Integer maxFallasDiarias);
}
