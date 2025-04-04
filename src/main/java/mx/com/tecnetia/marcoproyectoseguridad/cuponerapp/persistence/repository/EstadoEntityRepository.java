package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.EstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoEntityRepository extends JpaRepository<EstadoEntity, Integer> {
    EstadoEntity findByIdEstado(Integer idEstado);
}