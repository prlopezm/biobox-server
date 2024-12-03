package mx.com.tecnetia.orthogonal.beans;

import java.util.List;

import lombok.Data;
import mx.com.tecnetia.orthogonal.dto.ServicioRestDTO;

@Data
public class ServicioRestDTOBean {
    private List<ServicioRestDTO> listaServiciosActivos;
}
