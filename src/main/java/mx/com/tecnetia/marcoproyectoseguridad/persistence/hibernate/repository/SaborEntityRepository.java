package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.SaborEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaborEntityRepository extends JpaRepository<SaborEntity, Integer> {
}