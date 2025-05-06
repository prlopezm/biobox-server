package mx.com.tecnetia.orthogonal.ampq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoReciclableDTO {

    private Integer idProductoReciclable;
    private String sku;
    private String barCode;
    private Integer idMaterial;
    private Integer idSubMarca;
    private Integer idFabricante;
    private Integer idCapacidad;
    private MaterialDTO materialByIdMaterial;
    private SubMarcaDTO subMarcaByIdSubMarca;
    private CapacidadDTO capacidadByIdCapacidad;
    private Double pesoMinimo;
    private Double pesoMaximo;
    private FabricanteDTO fabricante;
}
