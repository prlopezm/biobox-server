package mx.com.tecnetia.marcoproyectoseguridad.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.DiaReciclajeBloqueadoDTO;

public class DiaReciclajeBloqueadoRowMapper implements RowMapper<DiaReciclajeBloqueadoDTO> {
    @Override
    public DiaReciclajeBloqueadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        var ent = new DiaReciclajeBloqueadoDTO(
                rs.getDate("fecha"),
                rs.getInt("cant")
        );

        return ent;
    }
}
