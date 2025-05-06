package mx.com.tecnetia.orthogonal.ampq.eventos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.tecnetia.orthogonal.ampq.dto.UsuarioPuntosColorDTO;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ActualizaPuntosEvento {
    private UsuarioPuntosColorDTO usuarioPuntosColorEntities;
}
