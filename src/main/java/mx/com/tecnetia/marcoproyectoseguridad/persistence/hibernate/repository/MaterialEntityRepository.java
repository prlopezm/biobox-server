package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialEntityRepository extends JpaRepository<MaterialEntity, Integer> {
    Optional<MaterialEntity> findByNombre(String nombre);
}
