package mx.com.tecnetia.orthogonal.persistence.hibernate.repository;

import java.util.List;

import mx.com.tecnetia.orthogonal.dto.ServicioRestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqServicioRestEntity;


@Repository
public interface ArqServicioRestRepository extends JpaRepository<ArqServicioRestEntity, Long> {

    @Query("""
             SELECT NEW mx.com.tecnetia.orthogonal.dto.ServicioRestDTO(ent.codigo, ent.arqMetodoServicioByCodigoMetodoServicio.codigoMetodoServicio,
                    concat(ent.arqModuloByIdArqModulo.http,'://' ,ent.arqModuloByIdArqModulo.ip,':',ent.arqModuloByIdArqModulo.port ,ent.arqModuloByIdArqModulo.uri,ent.uri))
            FROM ArqServicioRestEntity ent
            WHERE ent.activo = true
             """)
    List<ServicioRestDTO> getServicios();


}
