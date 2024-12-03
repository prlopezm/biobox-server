package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.MarcaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaEntityRepository extends JpaRepository<MarcaEntity, Integer> {
}