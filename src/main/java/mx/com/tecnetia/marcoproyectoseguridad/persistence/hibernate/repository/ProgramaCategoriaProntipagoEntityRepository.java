package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaCategoriaProntipagoEntity;

public interface ProgramaCategoriaProntipagoEntityRepository extends JpaRepository<ProgramaCategoriaProntipagoEntity, Long> {

	@Query("""
            select pc from ProgramaCategoriaProntipagoEntity pc where pc.tipoPrograma = :tipoPrograma
            and (pc.vigenciaInicio is null or pc.vigenciaInicio <= :fechaActual)
            and (pc.vigenciaFin is null or pc.vigenciaFin >= :fechaActual)
           """)
	List<ProgramaCategoriaProntipagoEntity> findByTipoProgramaAndIsValid(@Param("tipoPrograma")  String tipoPrograma, @Param("fechaActual")  Timestamp fechaActual);
	
	@Query("""
            select pc from ProgramaCategoriaProntipagoEntity pc where pc.tipoPrograma != :tipoPrograma
            and (pc.vigenciaInicio is null or pc.vigenciaInicio <= :fechaActual)
            and (pc.vigenciaFin is null or pc.vigenciaFin >= :fechaActual)
           """)
    List<ProgramaCategoriaProntipagoEntity> findByTipoProgramaIsNotAndIsValid(@Param("tipoPrograma")  String tipoPrograma, @Param("fechaActual")  Timestamp fechaActual);
    
}