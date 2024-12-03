package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.TokenCelEntity;

public interface TokenCelEntityRepository extends JpaRepository<TokenCelEntity, Long> {
    Optional<TokenCelEntity> findByToken(String token);
}