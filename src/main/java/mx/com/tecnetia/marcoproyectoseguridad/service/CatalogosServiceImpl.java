package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AnuncioDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AyudaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.LineaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.QuioscoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.AnuncioConcienciaEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.AnuncioEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.AyudaEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.EstacionMetroEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.LineaEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.QuioscoEntityRepository;

@Service
@RequiredArgsConstructor
public class CatalogosServiceImpl implements CatalogosService {
    private final EstacionMetroEntityRepository estacionMetroEntityRepository;
    private final AyudaEntityRepository ayudaEntityRepository;
    private final AnuncioEntityRepository anuncioEntityRepository;
    private final AnuncioConcienciaEntityRepository anuncioConcienciaEntityRepository;
    private final QuioscoEntityRepository quioscoEntityRepository;
    private final LineaEntityRepository lineaEntityRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LineaDTO> getAllLineasMetro() {
        List<LineaDTO> lineas = new ArrayList<LineaDTO>();

        for(LineaDTO linea : this.lineaEntityRepository.getAllDTO()){
            linea.setEstaciones(this.estacionMetroEntityRepository.getAllDTOByLinea(linea.getIdLinea()));
            lineas.add(linea);
        }

        return lineas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AyudaDTO> getAllAyudas() {
        return this.ayudaEntityRepository.getAllDTO();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnuncioDTO> getAllAnuncios() {
        return this.anuncioEntityRepository.getAllDTO();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnuncioDTO> getAllAnunciosEducativos() {
        return this.anuncioConcienciaEntityRepository.getAllDTO();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuioscoDTO> getAllQuioscos() {
        return this.quioscoEntityRepository.getAllDTO();
    }
}
