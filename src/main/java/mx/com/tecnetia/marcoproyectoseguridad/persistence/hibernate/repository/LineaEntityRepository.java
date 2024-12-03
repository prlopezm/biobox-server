package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.LineaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.LineaEntity;

@Repository
public interface LineaEntityRepository extends JpaRepository<LineaEntity, Integer> {

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.LineaDTO
            (ent.idLinea, ent.numero, ent.color, ent.foto)
            from LineaEntity ent
            """)
    List<LineaDTO> getAllDTO();
}