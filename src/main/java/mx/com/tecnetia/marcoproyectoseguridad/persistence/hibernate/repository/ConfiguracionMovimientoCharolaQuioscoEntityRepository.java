package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ConfiguracionMovimientoCharolaQuioscoEntity;

public interface ConfiguracionMovimientoCharolaQuioscoEntityRepository extends JpaRepository<ConfiguracionMovimientoCharolaQuioscoEntity, Long> {
    List<ConfiguracionMovimientoCharolaQuioscoEntity> getByIdQuioscoAndIdMaterial(Long idQuiosco, Integer idMaterial);
}