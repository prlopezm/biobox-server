package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorConsumidosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioPuntosColorConsumidosEntityRepository extends JpaRepository<UsuarioPuntosColorConsumidosEntity, Long> {

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO
            (c.idColor,c.nombre, c.hexadecimal, ent.puntos, c.urlFoto, c.urlFoto2)
            from UsuarioPuntosColorConsumidosEntity ent join ent.colorByIdColor c 
            where ent.idServicioUsado = :idServicioUsado order by c.nombre asc
            """)
    List<PuntosColorDTO> getPuntosByCanje(@Param("idServicioUsado") Long idServicioUsado);
}