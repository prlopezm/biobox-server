package mx.com.tecnetia.orthogonal.ampq.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorDTO {

    private Integer idColor;
    private String nombre;
    private String hexadecimal;
    private String urlFoto;
    private String urlFoto2;
}
