package mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "DTO de puntos para un color.")
public class PuntosColorDTO {
	private Long idPuntosRequeridos;
    private boolean habilitado;
    private Integer idColor;
    private String nombreColor;
    private String color;
    private Integer puntos;
    private String urlFoto;
    private String urlFoto2;

    public PuntosColorDTO(Long idPuntosRequeridos, boolean habilitado, Integer idColor, String nombre, String hexadecimal, Integer puntos, String urlFoto, String urlFoto2){
        this.idPuntosRequeridos = idPuntosRequeridos;
        this.habilitado = habilitado;
        this.idColor = idColor;
        this.nombreColor = nombre;
        this.color = hexadecimal;
        this.puntos = puntos;
        this.urlFoto = urlFoto;
        this.urlFoto2 = urlFoto2;                
    }
    
    public PuntosColorDTO(Integer idColor, String nombre, String hexadecimal, Integer puntos, String urlFoto, String urlFoto2){
        this.nombreColor = nombre;
        this.color = hexadecimal;
        this.puntos = puntos;
        this.urlFoto = urlFoto;
        this.urlFoto2 = urlFoto2;
        this.idColor = idColor;        
    }

    public PuntosColorDTO(Integer idColor, String nombre, String hexadecimal, Integer puntos){
        this.nombreColor = nombre;
        this.color = hexadecimal;
        this.puntos = puntos;
        this.urlFoto = "";
        this.idColor = idColor;
    }
}
