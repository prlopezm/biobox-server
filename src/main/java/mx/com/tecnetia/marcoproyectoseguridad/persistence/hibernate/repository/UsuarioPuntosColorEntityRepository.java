package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorEntity;

public interface UsuarioPuntosColorEntityRepository extends JpaRepository<UsuarioPuntosColorEntity, Long> {
    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosColorDTO
            (c.idColor,c.nombre, c.hexadecimal, ent.puntos, c.urlFoto, c.urlFoto2)
            from UsuarioPuntosColorEntity ent join ent.colorByIdColor c 
            where ent.idArqUsuario = :idArqUsuario order by c.nombre asc
            """)
    List<PuntosColorDTO> getDTOByUsuario(@Param("idArqUsuario") Long idArqUsuario);

    @Query("""
            select  sum(ent.puntos)
            from UsuarioPuntosColorEntity ent
                        join ent.colorByIdColor c
            where ent.idArqUsuario = :idArqUsuario
            """)
    Optional<Integer> totalPuntosUsuario(@Param("idArqUsuario") Long idArqUsuario);
 
    Optional<UsuarioPuntosColorEntity> findByIdArqUsuario(@Param("idArqUsuario") Long idArqUsuario);
    
    Optional<UsuarioPuntosColorEntity> findByIdArqUsuarioAndIdColor(Long idArqUsuario, Integer idColor);
}
