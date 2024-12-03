package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del notificacion de un reciclaje terminado.")
public class ReciclajeTerminadoRequestDTO implements Serializable {
	
    private static final long serialVersionUID = 4324858735830572647L;
	private Long idUsuario;
    private Long idQuiosco;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdQuiosco() {
        return idQuiosco;
    }

    public void setIdQuiosco(Long idQuiosco) {
        this.idQuiosco = idQuiosco;
    }
}
