package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorTmpDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ConversionEntity;


public interface ConversionEntityRepository extends JpaRepository<ConversionEntity, Long> {
    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorTmpDTO
            (c.idColor,c.nombre, c.hexadecimal, ent.puntos,ent.montoCentavos, c.urlFoto, c.urlFoto2)
            from ConversionEntity ent join ent.colorByIdColor c
            where ent.fechaFin is null
            """)
    List<PuntosColorTmpDTO> getActiveDTOByPrice();
    
    Optional<ConversionEntity> getByFechaFinIsNull();
    
}