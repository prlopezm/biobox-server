package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "DTO del catálogo de equivalencia de puntos por subprograma de un programa de lealtad.")
public class PuntosSubprogramaLealtadDTO {
    private Long idPrograma;
    private String sku;
    private String nombre;
    private String descripcion;
    private BigDecimal monto;
    private List<PuntosColorDTO> puntosRequeridos;

    public PuntosSubprogramaLealtadDTO(Long idProgramaProntipago,String sku, String nombre, String descripcion, BigDecimal monto, List<PuntosColorDTO> puntosRequeridos){
        this.idPrograma = idProgramaProntipago;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sku = sku;
        this.monto = monto;
        this.puntosRequeridos = puntosRequeridos;
    }
}
