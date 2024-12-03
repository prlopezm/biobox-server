package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ServicioUsadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioUsadoEntityRepository extends JpaRepository<ServicioUsadoEntity, Long> {
}