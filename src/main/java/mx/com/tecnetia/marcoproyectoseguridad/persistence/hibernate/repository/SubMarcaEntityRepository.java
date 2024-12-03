package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.SubMarcaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubMarcaEntityRepository extends JpaRepository<SubMarcaEntity, Integer> {

    Optional<SubMarcaEntity> findByNombre(String nombre);
}
