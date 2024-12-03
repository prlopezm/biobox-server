package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.PesoProductoRecicladoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PesoProductoRecicladoEntityRepository extends JpaRepository<PesoProductoRecicladoEntity, Long> {
}