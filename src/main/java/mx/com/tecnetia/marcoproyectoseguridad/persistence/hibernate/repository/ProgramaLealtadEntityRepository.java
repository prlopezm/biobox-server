package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaLealtadEntity;

@Repository
public interface ProgramaLealtadEntityRepository extends JpaRepository<ProgramaLealtadEntity, Long> {
}