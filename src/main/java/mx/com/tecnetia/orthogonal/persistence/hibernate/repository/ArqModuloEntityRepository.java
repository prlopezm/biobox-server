package mx.com.tecnetia.orthogonal.persistence.hibernate.repository;

import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqModuloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArqModuloEntityRepository extends JpaRepository<ArqModuloEntity, Long> {

    Optional<ArqModuloEntity> findArqModuloEntityByCodigoAndIdArqAplicacion(String codigo, Integer idArqAplicacion);
}