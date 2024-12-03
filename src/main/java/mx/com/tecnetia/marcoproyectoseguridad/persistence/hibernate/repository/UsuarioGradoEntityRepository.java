package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioGradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioGradoEntityRepository extends JpaRepository<UsuarioGradoEntity, Long> {
}