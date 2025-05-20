package mx.com.tecnetia.orthogonal.ampq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPuntosColorAcumuladosDTO {

    private Long idUsuarioPuntosColorAcumulado;
    private Integer puntos;
    private Integer idColor;
    private Long idProductoReciclado;
    private ColorDTO colorByIdColor;
}
