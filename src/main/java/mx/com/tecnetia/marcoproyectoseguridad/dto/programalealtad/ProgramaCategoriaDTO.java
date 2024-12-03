package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de equivalencia de puntos por programa de lealtad.")
public class ProgramaCategoriaDTO {
    private Long idCategoriaPrograma;
    private String nombre;
    private String descripcion;
    private String colorFondo;
    private String tipoPrograma;
    private String urlLogo;
    @JsonIgnore
    private String urlLogo2;
    @JsonIgnore
    private String urlPortal;    
    @JsonIgnore
    private boolean canjeUnico;
    @JsonIgnore
    private Timestamp vigenciaInicio;
    @JsonIgnore
    private Timestamp vigenciaFin;
    @JsonIgnore
    private boolean vigente;
    private List<PuntosColorDTO> puntosRequeridos;
    private List<ProgramaSubprogramaDTO> subprogramas;

    public ProgramaCategoriaDTO(Long idCategoriaPrograma,String nombre, String descripcion, String colorFondo, String tipoPrograma, String urlLogo, List<ProgramaSubprogramaDTO> subprogramas){
        this.idCategoriaPrograma = idCategoriaPrograma;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.colorFondo = colorFondo;
        this.tipoPrograma = tipoPrograma;
        this.urlLogo = urlLogo;
        this.subprogramas = subprogramas;
    }

    public ProgramaCategoriaDTO(Long idCategoriaPrograma, String nombre, String descripcion, String colorFondo, String tipoPrograma, String urlLogo){
        this.idCategoriaPrograma = idCategoriaPrograma;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.colorFondo = colorFondo;
        this.tipoPrograma = tipoPrograma;
        this.urlLogo = urlLogo;
        this.subprogramas = new ArrayList<ProgramaSubprogramaDTO>();
    }
}
