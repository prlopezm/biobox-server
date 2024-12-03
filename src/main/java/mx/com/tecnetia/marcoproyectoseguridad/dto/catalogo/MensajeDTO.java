package mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para regresar mensajes al front end y a su vez al usuario final.")
public class MensajeDTO<T> {
	
	private String estatus;
    private String mensaje;    
    private T data;
    
    public MensajeDTO(String mensaje) {
    	this.mensaje = mensaje;
    }
    
    public MensajeDTO(String estatus, String mensaje) {
    	this.estatus = estatus;
    	this.mensaje = mensaje;
    }
    
}
