package mx.com.tecnetia.orthogonal.ampq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPuntosColorDTO {

    private Long idUsuarioPuntosColor;
    private Long idArqUsuario;
    private Integer puntos;
    private Integer idColor;
    private ColorDTO colorByIdColor;
}
