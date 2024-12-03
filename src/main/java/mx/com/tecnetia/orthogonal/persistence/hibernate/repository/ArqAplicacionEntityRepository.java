package mx.com.tecnetia.orthogonal.persistence.hibernate.repository;

import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqAplicacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArqAplicacionEntityRepository extends JpaRepository<ArqAplicacionEntity, Long> {

    Optional<ArqAplicacionEntity> findByCodigoAndActivo(String codigo, Boolean activo);
}