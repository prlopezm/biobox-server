package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappUsadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CuponerappUsadaEntityRepository extends JpaRepository<CuponerappUsadaEntity, Long> {

    @Query("""
                select u
                from CuponerappUsadaEntity u
                    join fetch u.cuponUsado cuponUsado
                where (u.momento between :fechaInicio and :fechaFin)
                    and u.arqUsuarioId = :arqUsuarioId
            """)
    List<CuponerappUsadaEntity> usadosEntreFechasDeUsuario(@Param("fechaInicio") LocalDateTime fechaInicio,
                                                           @Param("fechaFin") LocalDateTime fechaFin,
                                                           @Param("arqUsuarioId") Long arqUsuarioId);

    @Query("""
                select u
                from CuponerappUsadaEntity u
                    join fetch u.cuponUsado cuponUsado
                where u.arqUsuarioId = :idArqUsuario
            """)
    List<CuponerappUsadaEntity> usadasPor(@Param("idArqUsuario") Long idArqUsuario);
}
