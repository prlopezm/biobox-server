package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-31 11:34 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para el histórico de canjes distintos a los prontipagos.")
public class HistoricoCanjeDTO implements Comparable<HistoricoCanjeDTO>{
    private String nombrePromocion;
    private Integer puntosUsados;
    private LocalDateTime fecha;
    private String folio;
    private String codigoQr;
    private String imagenBase64;
    private Boolean llevaDetalle;
    private String tipo;

    @Override
    public int compareTo(HistoricoCanjeDTO o) {
        return this.getFecha().isAfter(o.getFecha()) ? -1 : 1;
    }
}
