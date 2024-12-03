package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de equivalencia de puntos por subprograma de un programa de lealtad.")
public class ProgramaSubprogramaDTO {
    private Long idPrograma;
    private String sku;
    private String nombre;
    private String descripcion;
    private BigDecimal monto;    
    @JsonIgnore
    private Timestamp vigenciaInicio;
    @JsonIgnore
    private Timestamp vigenciaFin;
    @JsonIgnore
    private boolean vigente;
    @JsonIgnore
    private PuntosRequeridosDTO programaPuntosRequeridos;
    @JsonIgnore
    private PuntosRequeridosValidadosDTO puntos;
    private List<PuntosColorDTO> puntosRequeridos;

    public ProgramaSubprogramaDTO(Long idProgramaProntipago,String sku, String nombre, String descripcion, BigDecimal monto, List<PuntosColorDTO> puntosRequeridos){
        this.idPrograma = idProgramaProntipago;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sku = sku;
        this.monto = monto;
        this.puntosRequeridos = puntosRequeridos;
    }
}
