package mx.com.tecnetia.orthogonal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
    private Long idRol;
    private String nombre;
    private String descripcion;
}
