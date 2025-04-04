package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service;

import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CategoriaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.EstadoCategoriaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.EstadoDTO;

import java.util.List;

public interface CatalogosCuponeraService {
    List<EstadoDTO> getEstados();

    List<CategoriaDTO> getCategorias();

    List<EstadoCategoriaDTO> getEstadosCategorias();
}
