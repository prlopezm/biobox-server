package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappCategoriaEstadoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuponeraCategoriaEstadoDTO {
    private Integer idEstado;
    private Integer idCategoria;

    public CuponeraCategoriaEstadoDTO(CuponerappCategoriaEstadoEntity cuponerappCategoriaEstadoEntity) {
       this.idEstado = cuponerappCategoriaEstadoEntity.getEstado().getIdEstado();
       this.idCategoria = cuponerappCategoriaEstadoEntity.getCategoria().getIdCategoria();
    }
}
