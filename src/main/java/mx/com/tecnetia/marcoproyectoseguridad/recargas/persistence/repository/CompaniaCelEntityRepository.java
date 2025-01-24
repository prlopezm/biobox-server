package mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.entity.CompaniaCelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniaCelEntityRepository extends JpaRepository<CompaniaCelEntity, Integer> {
}
