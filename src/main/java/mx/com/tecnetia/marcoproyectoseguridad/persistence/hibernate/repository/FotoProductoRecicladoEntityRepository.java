package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.FotoProductoRecicladoEntity;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoProductoRecicladoEntityRepository extends JpaRepository<FotoProductoRecicladoEntity, Long> {
	
	ArrayList<FotoProductoRecicladoEntity> findByIdFotoProductoRecicladoIsIn(Collection<Long> ids);
	
}