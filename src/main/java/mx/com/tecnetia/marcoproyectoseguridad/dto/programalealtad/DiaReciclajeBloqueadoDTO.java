package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import java.sql.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "DTO de reciclajes fallidos por dia.")
public class DiaReciclajeBloqueadoDTO {
    private Date fecha;
    private Integer reciclajesFallidos;

    public DiaReciclajeBloqueadoDTO(Date fecha, Integer reciclajesFallidos) {

        this.fecha = fecha;
        this.reciclajesFallidos = reciclajesFallidos;
    }
}
