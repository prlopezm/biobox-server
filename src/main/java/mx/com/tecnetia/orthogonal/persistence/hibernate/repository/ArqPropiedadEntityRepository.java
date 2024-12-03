package mx.com.tecnetia.orthogonal.persistence.hibernate.repository;

import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqPropiedadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArqPropiedadEntityRepository extends JpaRepository<ArqPropiedadEntity, Long> {
    Optional<ArqPropiedadEntity> findArqPropiedadEntitiesByActivoIsTrueAndCodigo(String codigo);
    List<ArqPropiedadEntity> getArqPropiedadEntitiesByGrupoCodigo(String grupoCodigo);
}