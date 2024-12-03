package mx.com.tecnetia.orthogonal.services;

import mx.com.tecnetia.orthogonal.dto.RolDTO;
import mx.com.tecnetia.orthogonal.dto.ServicioRestDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CatalogoService {

    List<ServicioRestDTO> getCatalogoServiciosRest();
    CompletableFuture<List<ServicioRestDTO>> getCatalogoServiciosRest2();
    List<RolDTO> getAllRoles();
}
