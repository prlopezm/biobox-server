package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosProgramaLealtadDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.CategoriaProntipagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaProntipagoEntityRepository extends JpaRepository<CategoriaProntipagoEntity, Long> {

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosProgramaLealtadDTO
            (cat.idCategoriaProntipago,  cat.nombre, cat.descripcion, cat.colorFondo, cat.tipoPrograma, cat.urlLogo)
            from CategoriaProntipagoEntity cat where cat.tipoPrograma<>:tipoPrograma order by cat.idCategoriaProntipago
            """)
    List<PuntosProgramaLealtadDTO> getAllDTODiferentTipoPrograma(@Param("tipoPrograma") String tipoPrograma);

}