package mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository;

import jakarta.validation.constraints.NotNull;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity.CanjeOxxoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CanjeOxxoEntityRepository extends JpaRepository<CanjeOxxoEntity, Long> {

    @Query("""
            select sum(oco.puntosCanjear)
            from CanjeOxxoEntity co
                join co.opcionCanjeOxxo oco
            where EXTRACT(YEAR FROM co.momento) = EXTRACT(YEAR FROM CURRENT_DATE)
                and EXTRACT(MONTH FROM co.momento) = EXTRACT(MONTH FROM CURRENT_DATE)
                and co.arqUsuarioId = :arqUsuarioId
            """)
    Optional<Integer> canjesMesActualUsuario(@Param("arqUsuarioId") @NotNull Long arqUsuarioId);

    @Query("""
            select ent
                from CanjeOxxoEntity ent
                join fetch ent.opcionCanjeOxxo
            where ent.arqUsuarioId = :idArqUsuario
            """)
    List<CanjeOxxoEntity> usadasPor(@Param("idArqUsuario") Long idArqUsuario);
}
