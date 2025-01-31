package mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.entity.DenominacionRecargaCelUsadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenominacionRecargaCelUsadaEntityRepository extends JpaRepository<DenominacionRecargaCelUsadaEntity, Long> {

    @Query("""
            SELECT r
            from DenominacionRecargaCelUsadaEntity r
                join fetch r.denominacionRecargaCel drc
                join fetch drc.companiaCel comp
            where r.arqUsuarioId = :idArqUsuario
            """)
    List<DenominacionRecargaCelUsadaEntity> usadasPor(@Param("idArqUsuario") Long idArqUsuario);
}
