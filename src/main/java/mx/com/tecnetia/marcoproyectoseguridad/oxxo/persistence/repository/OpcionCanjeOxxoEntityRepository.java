package mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.OpcionCanjeOxxoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity.OpcionCanjeOxxoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcionCanjeOxxoEntityRepository extends JpaRepository<OpcionCanjeOxxoEntity, Integer> {

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.OpcionCanjeOxxoDTO(ent.id, ent.nombre, ent.puntosCanjear)
            from OpcionCanjeOxxoEntity ent
            """)
    List<OpcionCanjeOxxoDTO> getAllDTO();
}
