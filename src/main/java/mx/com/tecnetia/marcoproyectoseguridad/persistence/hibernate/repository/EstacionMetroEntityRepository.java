package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.EstacionMetroDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.EstacionMetroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstacionMetroEntityRepository extends JpaRepository<EstacionMetroEntity, Long> {

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.EstacionMetroDTO
            (ent.idEstacionMetro, ent.nombre, ent.latitud, ent.longitud, ent.foto, ent.codigo)
            from EstacionMetroEntity ent
            """)
    List<EstacionMetroDTO> getAllDTO();

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.EstacionMetroDTO
            (ent.idEstacionMetro, ent.nombre, ent.latitud, ent.longitud, ent.foto, ent.codigo)
            from EstacionMetroEntity ent where ent.idLinea = :idLinea
            """)
    List<EstacionMetroDTO> getAllDTOByLinea(@Param("idLinea") Integer idLinea);
}