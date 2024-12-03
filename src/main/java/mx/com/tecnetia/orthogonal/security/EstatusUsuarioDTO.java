package mx.com.tecnetia.orthogonal.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstatusUsuarioDTO {
    private boolean pendienteConfirmacion;
    private boolean existeUsuario;
}
