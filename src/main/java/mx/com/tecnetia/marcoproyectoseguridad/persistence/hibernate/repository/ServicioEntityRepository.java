package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicioEntityRepository extends JpaRepository<ServicioEntity, Long> {
    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO
            (c.idColor,c.nombre, c.hexadecimal, ent.puntos, c.urlFoto, c.urlFoto2)
            from ServicioEntity ent join ent.colorByIdColor c where ent.programaProntipago.idProgramaProntipago = :idProgramaProntipago
            and ent.fechaFin is null
            """)
    List<PuntosColorDTO> getActiveDTOByProgramaProntipago(@Param("idProgramaProntipago") Long idProgramaProntipago);
}