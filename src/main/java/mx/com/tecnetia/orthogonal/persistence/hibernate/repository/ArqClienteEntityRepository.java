package mx.com.tecnetia.orthogonal.persistence.hibernate.repository;

import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArqClienteEntityRepository extends JpaRepository<ArqClienteEntity, Long> {

    Optional<ArqClienteEntity> findByCodigoAndActivoIsTrue(String codigo);
}