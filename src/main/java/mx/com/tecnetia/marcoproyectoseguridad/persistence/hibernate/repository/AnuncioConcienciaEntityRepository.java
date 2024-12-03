package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AnuncioDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.AnuncioConcienciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioConcienciaEntityRepository extends JpaRepository<AnuncioConcienciaEntity, Long> {
    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AnuncioDTO
            (ent.idAnuncioConciencia, ent.urlFoto)
            from AnuncioConcienciaEntity ent order by ent.idAnuncioConciencia asc
            """)
    List<AnuncioDTO> getAllDTO();
}