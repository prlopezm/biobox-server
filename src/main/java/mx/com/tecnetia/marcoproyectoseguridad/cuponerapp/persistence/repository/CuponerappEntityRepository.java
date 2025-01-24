package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuponerappEntityRepository extends JpaRepository<CuponerappEntity, Integer> {

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponDTO(c.idPromocion, c.puntos)
            from CuponerappEntity c
            """)
    List<CuponDTO> getCupones();

    Optional<CuponerappEntity> findByIdPromocion(Integer idPromocion);

}
