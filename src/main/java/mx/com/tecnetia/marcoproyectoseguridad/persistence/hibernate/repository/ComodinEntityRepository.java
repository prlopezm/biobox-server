package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ComodinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComodinEntityRepository extends JpaRepository<ComodinEntity, Long> {
}