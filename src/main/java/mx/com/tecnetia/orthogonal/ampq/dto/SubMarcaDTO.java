package mx.com.tecnetia.orthogonal.ampq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubMarcaDTO {

    private Integer idSubMarca;
    private String nombre;
    private Integer idMarca;
    private MarcaDTO marcaByIdMarca;
}
