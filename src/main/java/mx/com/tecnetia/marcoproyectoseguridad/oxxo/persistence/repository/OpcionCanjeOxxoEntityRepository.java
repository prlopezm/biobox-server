package mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity.OpcionCanjeOxxoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionCanjeOxxoEntityRepository extends JpaRepository<OpcionCanjeOxxoEntity, Integer> {
}
