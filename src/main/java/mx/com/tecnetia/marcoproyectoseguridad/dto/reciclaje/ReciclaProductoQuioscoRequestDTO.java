package mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje;


import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO del reciclado de un producto en máquina.")
public class ReciclaProductoQuioscoRequestDTO implements Serializable {
	
    private static final long serialVersionUID = -5024207917778537757L;
	private Long idProducto;
    private Long idQuiosco;
    private Long idUsuario;
    private boolean moverCharolaALaDerecha;
    private int tipoPic;
    private String barCode;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdQuiosco() {
        return idQuiosco;
    }

    public void setIdQuiosco(Long idQuiosco) {
        this.idQuiosco = idQuiosco;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isMoverCharolaALaDerecha() {
        return moverCharolaALaDerecha;
    }

    public void setMoverCharolaALaDerecha(boolean moverCharolaALaDerecha) {
        this.moverCharolaALaDerecha = moverCharolaALaDerecha;
    }

    public int getTipoPic() {
        return tipoPic;
    }

    public void setTipoPic(int tipoPic) {
        this.tipoPic = tipoPic;
    }
}
