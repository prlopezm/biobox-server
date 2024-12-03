package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosUsuarioDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioPuntosEntityRepository extends JpaRepository<UsuarioPuntosEntity, Long> {
    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosUsuarioDTO
            (ent.puntos)
            from UsuarioPuntosEntity ent where ent.idArqUsuario = :idArqUsuario
            """)
    PuntosUsuarioDTO getDTOByUsuario(@Param("idArqUsuario") Long idArqUsuario);
}