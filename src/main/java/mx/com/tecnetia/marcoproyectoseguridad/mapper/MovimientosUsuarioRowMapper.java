package mx.com.tecnetia.marcoproyectoseguridad.mapper;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.MovimientosDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovimientosUsuarioRowMapper implements RowMapper<MovimientosDTO> {
    @Override
    public MovimientosDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        var ent = new MovimientosDTO(
                rs.getLong("idProductoReciclado"),
                rs.getTimestamp("fechaHora").toLocalDateTime(),
                rs.getString("descripcion"),
                rs.getInt("tipoMovimiento")
        );
        return ent;
    }
}
