package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappUsadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuponerappUsadaEntityRepository extends JpaRepository<CuponerappUsadaEntity, Long> {
}
