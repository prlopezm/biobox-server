package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.ProductoAReciclarDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProductoReciclableEntity;

public interface ProductoReciclableEntityRepository extends JpaRepository<ProductoReciclableEntity, Long> {
    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.ProductoAReciclarDTO
            (ent.idProductoReciclable,ent.sku, ent.idMaterial)
            from ProductoReciclableEntity ent where ent.barCode = :barCode
            """)
    Optional<ProductoAReciclarDTO> getProductoByBarCode(@Param("barCode") String barCode);

    @Query("""
            select new mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.ProductoAReciclarDTO
            (ent.idProductoReciclable,ent.sku, ent.idMaterial)
            from ProductoReciclableEntity ent where ent.barCode IN :barCodes
            """)
    List<ProductoAReciclarDTO> getProductosByBarCodes(@Param("barCodes") List<String> barCodes);

    Optional<ProductoReciclableEntity> findBySku(String sku);


}
