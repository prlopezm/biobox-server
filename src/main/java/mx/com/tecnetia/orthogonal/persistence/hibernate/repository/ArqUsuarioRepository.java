package mx.com.tecnetia.orthogonal.persistence.hibernate.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;

@Repository
public interface ArqUsuarioRepository extends JpaRepository<ArqUsuarioEntity, Long> {

    @Query("select ent from ArqUsuarioEntity ent where lower(trim(ent.nick)) = lower(trim(:nick))")
    Optional<ArqUsuarioEntity> findByNick(@Param("nick") String nick);

    @Query("select ent from ArqUsuarioEntity ent where lower(trim(ent.email)) = lower(trim(:email))")
    Optional<ArqUsuarioEntity> findByEmail(@Param("email") String email);
    
    @Query("select ent from ArqUsuarioEntity ent where lower(trim(ent.telefono)) = lower(trim(:telefono))")
    Optional<ArqUsuarioEntity> findByTelefono(@Param("telefono") String telefono);

    List<ArqUsuarioEntity> findByTelefonoAllIgnoreCase(@NonNull String telefono);

}
