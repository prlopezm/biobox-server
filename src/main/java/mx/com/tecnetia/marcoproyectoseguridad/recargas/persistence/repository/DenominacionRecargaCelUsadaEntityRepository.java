package mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.entity.DenominacionRecargaCelUsadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DenominacionRecargaCelUsadaEntityRepository extends JpaRepository<DenominacionRecargaCelUsadaEntity, Long> {
}
