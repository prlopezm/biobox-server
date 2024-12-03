package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.util.List;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.MovimientosDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.ProgramaCategoriaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosUsuarioDTO;

public interface ProgramaLealtadService {

    PuntosUsuarioDTO getPuntosUsuario(Long idArqUsuario);
    List<PuntosColorDTO> getPuntosUsuarioXColor(Long idArqUsuario);
    List<MovimientosDTO> getUltimosMovimientosDeUsuario(Long idArqUsuario);
    List<ProgramaCategoriaDTO> getProgramasLealtad(Long idArqUsuario);
    List<ProgramaCategoriaDTO> getProgramasLealtadFake();
    
}
