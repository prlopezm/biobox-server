package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de los saldos y movimientos de un usuario.")
public class MovimientosDTO {
    private Long idProductoReciclado;
    private LocalDateTime fechaHora;
    private Integer tipoMovimiento;
    private String descripcion;
    private List<PuntosColorDTO> puntos;

    public MovimientosDTO(Long idProductoReciclado, LocalDateTime fechaHora, String descripcion, Integer tipoMovimiento) {

        this.idProductoReciclado = idProductoReciclado;
        this.fechaHora = fechaHora;
        this.tipoMovimiento = tipoMovimiento;
        this.descripcion = descripcion;
    }
}
