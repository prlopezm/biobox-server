package mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.repository;

import mx.com.tecnetia.marcoproyectoseguridad.recargas.dto.DenominacionRecargaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.entity.DenominacionRecargaCelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenominacionRecargaCelEntityRepository extends JpaRepository<DenominacionRecargaCelEntity, Integer> {

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.recargas.dto.DenominacionRecargaDTO(
                        ent.id, comp.nombre, comp.imgLogo, comp.idProduct, ent.monto, ent.puntos
                        )
            from DenominacionRecargaCelEntity ent
                join ent.companiaCel comp
            """)
    List<DenominacionRecargaDTO> getAllDenominacionRecargaCel();
}
