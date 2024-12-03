package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorEntityRepository extends JpaRepository<ColorEntity, Integer> {
}