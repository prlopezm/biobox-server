package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del reciclado de un producto en máquina V2.")
public class ReciclaProductoQuioscoRequestV2DTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5024207917778537757L;

    private List<Long> idsProducto;
    private Long idQuiosco;
    private Long idUsuario;
    private boolean moverCharolaALaDerecha;
    private int tipoPic;
    private String barCode;
}
