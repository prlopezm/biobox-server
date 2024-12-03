package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.QuioscoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.QuioscoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuioscoEntityRepository extends JpaRepository<QuioscoEntity, Long> {

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.QuioscoDTO
            (ent.idQuiosco, ent.direccion, ent.latitud, ent.longitud, ent.nombre)
            from QuioscoEntity ent
            """)
    List<QuioscoDTO> getAllDTO();

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.QuioscoDTO
            (ent.idQuiosco, ent.direccion, ent.latitud, ent.longitud, ent.nombre)
            from QuioscoEntity ent where ent.qr = :qr 
            """)
    Optional<QuioscoDTO> getByQr(@Param("qr") UUID qr);
}