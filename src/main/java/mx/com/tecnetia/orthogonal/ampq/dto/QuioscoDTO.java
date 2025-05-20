package mx.com.tecnetia.orthogonal.ampq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuioscoDTO {

    private Long idQuiosco;
    private String direccion;
    private String latitud;
    private String longitud;
    private java.util.UUID qr;
    private String ip;
    private Integer alturaLlenadoMm;
    private Boolean tipoArduino;
    private String nombre;
}
