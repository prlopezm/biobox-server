package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ImportacionUsuarioPuntosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportacionUsuarioPuntosEntityRepository extends JpaRepository<ImportacionUsuarioPuntosEntity, Long> {
}