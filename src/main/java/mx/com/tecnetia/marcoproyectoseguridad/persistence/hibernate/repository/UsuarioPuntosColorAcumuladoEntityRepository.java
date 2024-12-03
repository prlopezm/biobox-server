package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorAcumuladoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioPuntosColorAcumuladoEntityRepository extends JpaRepository<UsuarioPuntosColorAcumuladoEntity, Long> {
    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO
            (c.idColor,c.nombre, c.hexadecimal, ent.puntos, c.urlFoto, c.urlFoto2)
            from UsuarioPuntosColorAcumuladoEntity ent join ent.colorByIdColor c 
            where ent.idProductoReciclado = :idProductoReciclado order by c.nombre asc
            """)
    List<PuntosColorDTO> getPuntosByReciclaje(@Param("idProductoReciclado") Long idProductoReciclado);
}