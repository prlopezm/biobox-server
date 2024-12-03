package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.MovimientosDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProductoRecicladoEntity;

public interface ProductoRecicladoEntityRepository extends JpaRepository<ProductoRecicladoEntity, Long> {

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.MovimientosDTO
            (pr.idProductoReciclado, pr.momentoReciclaje,sm.nombre, 1)
            from 
            ProductoRecicladoEntity pr join pr.productoReciclableByIdProductoReciclable pr2 
            join pr2.materialByIdMaterial m 
            join pr2.subMarcaByIdSubMarca sm 
            join sm.marcaByIdMarca m2
            join pr2.capacidadByIdCapacidad c
            join c.unidadMedidaByIdUnidadMedida um
            where pr.idArqUsuario = :idArqUsuario
            order by pr.momentoReciclaje desc 
            LIMIT 30
            """)
    List<MovimientosDTO> getSaldosYMovimientosCliente(@Param("idArqUsuario") Long idArqUsuario);

    @Query("""
            select pr from ProductoRecicladoEntity pr where pr.idArqUsuario = :idArqUsuario
            and pr.exitoso = :exitoso and  cast(pr.momentoReciclaje as Date) = :fecha
            """)
    List<ProductoRecicladoEntity> getByFechaAndExitosoAndUsuario(@Param("idArqUsuario") Long idArqUsuario,@Param("fecha") Date fecha,@Param("exitoso") Boolean exitoso);

    @Query("""
            select pr from ProductoRecicladoEntity pr where pr.idArqUsuario = :idArqUsuario
            and  cast(pr.momentoReciclaje as Date) = :fecha
            """)
    List<ProductoRecicladoEntity> getByFechaAndUsuario(@Param("idArqUsuario") Long idArqUsuario,@Param("fecha") Date fecha);

    @Query("""
            select pr from ProductoRecicladoEntity pr where pr.idArqUsuario = :idArqUsuario
            and pr.exitoso = :exitoso
            """)
    List<ProductoRecicladoEntity> getByExitosoAndUsuario(@Param("idArqUsuario") Long idArqUsuario,@Param("exitoso") Boolean exitoso);

}