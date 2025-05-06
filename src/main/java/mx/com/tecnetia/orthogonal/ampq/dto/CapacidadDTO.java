package mx.com.tecnetia.orthogonal.ampq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CapacidadDTO {

    private Integer idCapacidad;
    private Integer cantidad;
    private Integer idUnidadMedida;
    private UnidadMedidaDTO unidadMedidaByIdUnidadMedida;
}
