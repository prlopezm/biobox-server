package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AnuncioDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.AnuncioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioEntityRepository extends JpaRepository<AnuncioEntity, Long> {
    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AnuncioDTO
            (ent.idAnuncio, ent.urlFoto)
            from AnuncioEntity ent order by ent.idAnuncio asc
            """)
    List<AnuncioDTO> getAllDTO();
}