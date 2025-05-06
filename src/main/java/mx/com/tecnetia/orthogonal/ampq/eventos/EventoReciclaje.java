package mx.com.tecnetia.orthogonal.ampq.eventos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mx.com.tecnetia.orthogonal.ampq.dto.ProductoRecicladoDTO;
import mx.com.tecnetia.orthogonal.ampq.dto.QuioscoDTO;

@Data
@AllArgsConstructor
public class EventoReciclaje {

    private String pais;
    private ProductoRecicladoDTO productoReciclado;
    private QuioscoDTO quiosco;
}
