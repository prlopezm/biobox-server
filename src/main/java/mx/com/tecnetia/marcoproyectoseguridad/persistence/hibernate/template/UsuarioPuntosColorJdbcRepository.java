package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.template;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.MovimientosDTO;

import java.util.List;

public interface UsuarioPuntosColorJdbcRepository{
        List<MovimientosDTO> getMovimientosDeUsuario(Long idUsuario);
}
