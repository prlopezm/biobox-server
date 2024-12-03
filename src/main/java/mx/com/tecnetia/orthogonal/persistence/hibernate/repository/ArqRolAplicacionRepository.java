package mx.com.tecnetia.orthogonal.persistence.hibernate.repository;

import java.util.List;
import java.util.Optional;

import mx.com.tecnetia.orthogonal.dto.RolDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqRolEntity;

@Repository
public interface ArqRolAplicacionRepository extends JpaRepository<ArqRolEntity, Long> {

    Optional<ArqRolEntity> findByNombre(String nombre);

    @Query("""
            select new mx.com.tecnetia.orthogonal.dto.RolDTO(ent.idArqRol, ent.nombre, ent.descripcion)
            from ArqRolEntity ent
            """)
    List<RolDTO> getAllRolesDTO();


}
