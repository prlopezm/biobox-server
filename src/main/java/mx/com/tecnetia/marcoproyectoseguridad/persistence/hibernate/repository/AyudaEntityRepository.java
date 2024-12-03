package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AyudaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.AyudaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AyudaEntityRepository extends JpaRepository<AyudaEntity, Integer> {
    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AyudaDTO
            (ent.idAyuda,ent.nombre, ent.urlFoto, ent.texto)
            from AyudaEntity ent order by ent.anterior desc
            """)
    List<AyudaDTO> getAllDTO();
}