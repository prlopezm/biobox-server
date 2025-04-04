package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CategoriaEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Integer idCategoria;
    private String nombre;

    public CategoriaDTO(CategoriaEntity categoriaEntity) {
        this.idCategoria = categoriaEntity.getIdCategoria();
        this.nombre = categoriaEntity.getNombre();
    }
}