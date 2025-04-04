package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.EstadoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDTO {
    private Integer idEstado;
    private String nombre;

    public EstadoDTO(EstadoEntity estadoEntity) {
        this.idEstado = estadoEntity.getIdEstado();
        this.nombre = estadoEntity.getNombre();
    }
}
