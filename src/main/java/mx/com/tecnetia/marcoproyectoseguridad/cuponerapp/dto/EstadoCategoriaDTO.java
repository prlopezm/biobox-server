package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCategoriaDTO {
    private Integer idEstado;
    private String nombre;
    private List<CategoriaDTO> categorias;


}
