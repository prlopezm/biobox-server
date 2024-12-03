package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del catálogo de equivalencia de puntos por programa de lealtad.")
public class PuntosProgramaLealtadDTO {
    private Long idCategoriaPrograma;
    private String nombre;
    private String descripcion;
    private String colorFondo;
    private String urlLogo;
    private String tipoPrograma;
    private List<PuntosColorDTO> puntosRequeridos;
    private List<PuntosSubprogramaLealtadDTO> subprogramas;

    public PuntosProgramaLealtadDTO(Long idCategoriaPrograma,String nombre, String descripcion, String colorFondo, String tipoPrograma, String urlLogo, List<PuntosSubprogramaLealtadDTO> subprogramas){
        this.idCategoriaPrograma = idCategoriaPrograma;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.colorFondo = colorFondo;
        this.tipoPrograma = tipoPrograma;
        this.urlLogo = urlLogo;
        this.subprogramas = subprogramas;
    }

    public PuntosProgramaLealtadDTO(Long idCategoriaPrograma, String nombre, String descripcion, String colorFondo, String tipoPrograma, String urlLogo){
        this.idCategoriaPrograma = idCategoriaPrograma;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.colorFondo = colorFondo;
        this.tipoPrograma = tipoPrograma;
        this.urlLogo = urlLogo;
        this.subprogramas = new ArrayList<PuntosSubprogramaLealtadDTO>();
    }
}
