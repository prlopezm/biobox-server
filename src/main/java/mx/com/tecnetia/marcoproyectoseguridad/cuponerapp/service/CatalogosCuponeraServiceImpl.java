package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.*;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.EstadoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.CategoriaEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.CuponerappCategoriaEstadoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.EstadoEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class CatalogosCuponeraServiceImpl implements CatalogosCuponeraService{

    private final EstadoEntityRepository estadoEntityRepository;
    private final CategoriaEntityRepository categoriaEntityRepository;
    private final CuponerappCategoriaEstadoEntityRepository cuponerappCategoriaEstadoEntityRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDTO> getEstados() {
        return estadoEntityRepository.findAll()
                .stream()
                .map(EstadoDTO::new)
                .toList();
    }

    @Override
    public List<CategoriaDTO> getCategorias() {
        return categoriaEntityRepository.findAll()
                .stream()
                .map(CategoriaDTO::new)
                .toList();
    }

    @Override
    public List<EstadoCategoriaDTO> getEstadosCategorias() {
        List<CuponeraCategoriaEstadoDTO> list = cuponerappCategoriaEstadoEntityRepository.findDistinctByCategoriaAndEstado();

        Map<Integer, EstadoCategoriaDTO> estadoCategoriaMap = new HashMap<>();

        for (CuponeraCategoriaEstadoDTO dto : list) {
            EstadoCategoriaDTO estadoCategoriaDTO = estadoCategoriaMap.computeIfAbsent(dto.getIdEstado(), id -> {
                EstadoEntity estadoEntity = estadoEntityRepository.findByIdEstado(id);
                return new EstadoCategoriaDTO(id, estadoEntity.getNombre(), new ArrayList<>());
            });

            CategoriaDTO categoriaDTO = new CategoriaDTO(categoriaEntityRepository.findByIdCategoria(dto.getIdCategoria()));
            estadoCategoriaDTO.getCategorias().add(categoriaDTO);
        }

        return new ArrayList<>(estadoCategoriaMap.values());
    }

}
