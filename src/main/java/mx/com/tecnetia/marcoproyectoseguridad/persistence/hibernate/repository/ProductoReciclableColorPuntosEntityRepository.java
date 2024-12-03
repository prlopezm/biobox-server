package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ProductoReciclableColorPuntosEntity;

public interface ProductoReciclableColorPuntosEntityRepository extends JpaRepository<ProductoReciclableColorPuntosEntity, Long> {
    List<ProductoReciclableColorPuntosEntity> getByIdProductoReciclableAndFechaFinIsNull(Long idProductoReciclable);
}