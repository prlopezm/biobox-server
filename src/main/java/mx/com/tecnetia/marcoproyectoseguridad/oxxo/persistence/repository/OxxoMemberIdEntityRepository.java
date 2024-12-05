package mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity.OxxoMemberIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OxxoMemberIdEntityRepository extends JpaRepository<OxxoMemberIdEntity, Long> {
    Optional<OxxoMemberIdEntity> findByArqUsuarioId(Long arqUsuarioId);
}
