package mx.com.tecnetia.orthogonal.ampq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRecicladoDTO {
    private Long idProductoReciclado;
    private Timestamp momentoReciclaje;
    private Integer idProductoReciclable;
    private Integer idArqUsuario;
    private Boolean exitoso;
    private ProductoReciclableDTO productoReciclableByIdProductoReciclable;
    private UsuarioDTO arqUsuarioByIdArqUsuario;
    private List<UsuarioPuntosColorAcumuladosDTO> usuarioPuntosColorAcumulados;
}
