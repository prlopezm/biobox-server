package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.FabricanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FabricanteEntityRepository extends JpaRepository<FabricanteEntity, Integer> {
}