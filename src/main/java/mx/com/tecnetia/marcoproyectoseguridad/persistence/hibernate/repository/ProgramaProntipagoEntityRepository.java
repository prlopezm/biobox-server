package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaProntipagoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaPuntosRequeridosEntity;

public interface ProgramaProntipagoEntityRepository extends JpaRepository<ProgramaProntipagoEntity, Long> {
	
    Optional<ProgramaProntipagoEntity> findByCodigo(String sku);
    Optional<ProgramaProntipagoEntity> findByPuntosRequeridos(ProgramaPuntosRequeridosEntity puntosRequeridos);
    List<ProgramaProntipagoEntity> findByIdCategoriaProntipago(Long idCategoriaProntipago);
    
}