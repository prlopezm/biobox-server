package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.util.List;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AnuncioDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AyudaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.LineaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.QuioscoDTO;

public interface CatalogosService {

    List<LineaDTO> getAllLineasMetro();
    List<AyudaDTO> getAllAyudas();
    List<AnuncioDTO> getAllAnuncios();
    List<AnuncioDTO> getAllAnunciosEducativos();
    List<QuioscoDTO> getAllQuioscos();
}
