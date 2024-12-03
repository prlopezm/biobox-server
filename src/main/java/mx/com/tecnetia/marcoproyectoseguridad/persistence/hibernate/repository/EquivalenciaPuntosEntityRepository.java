package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.EquivalenciaPuntosEntity;

@Repository
public interface EquivalenciaPuntosEntityRepository extends JpaRepository<EquivalenciaPuntosEntity, Long> {

 /*   @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosProgramaLealtadDTO
            (ent.idProgramaLealtad, pl.nombre, ent.puntos)
            from EquivalenciaPuntosEntity ent join ent.programaLealtadByIdProgramaLealtad pl 
            where ent.fechaDesactivacion is null
            """)
    List<PuntosProgramaLealtadDTO> getAllDTOActive();*/
}