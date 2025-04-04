package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponeraCategoriaEstadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappCategoriaEstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuponerappCategoriaEstadoEntityRepository extends JpaRepository<CuponerappCategoriaEstadoEntity, Long> {

    List<CuponerappCategoriaEstadoEntity> findByCategoriaIdCategoriaAndEstadoIdEstado(Integer idCategoria, Integer idEstado);

    @Query("select distinct new mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponeraCategoriaEstadoDTO (c.estado.idEstado, c.categoria.idCategoria)"+
            "from CuponerappCategoriaEstadoEntity c")
    List<CuponeraCategoriaEstadoDTO> findDistinctByCategoriaAndEstado();

}