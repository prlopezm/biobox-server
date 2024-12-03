package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaCodigosDescuentoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProgramaPuntosRequeridosEntity;

public interface ProgramaCodigosDescuentoEntityRepository extends JpaRepository<ProgramaCodigosDescuentoEntity, Long> {

	@Query("""
            select p from ProgramaCodigosDescuentoEntity p where p.idArqUsuario = :idArqUsuario
            and p.programaPuntosRequeridos.programaProntipago.categoriaProntipago.idCategoriaProntipago = :idCategoria
           """)
	Optional<ProgramaCodigosDescuentoEntity> findByIdArqUsuarioAndIdCategoria(@Param("idArqUsuario") Long idArqUsuario, @Param("idCategoria") Long idCategoria);
	
	Optional<ProgramaCodigosDescuentoEntity> findFirstByIdArqUsuarioIsNullAndProgramaPuntosRequeridos(ProgramaPuntosRequeridosEntity puntosRequqridos);
	
}
