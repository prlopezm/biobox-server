package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "producto_reciclable_color_puntos", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ProductoReciclableColorPuntosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_producto_reciclable_color_puntos")
    private Long idProductoReciclableColorPuntos;
    @Basic
    @Column(name = "id_color", nullable = false, insertable = false, updatable = false)
    private Integer idColor;
    @Basic
    @Column(name = "id_producto_reciclable", nullable = false, insertable = false, updatable = false)
    private Long idProductoReciclable;
    @Basic
    @Column(name = "puntos")
    private Integer puntos;
    @Basic
    @Column(name = "fecha_inicio")
    private Timestamp fechaInicio;
    @Basic
    @Column(name = "fecha_fin")
    private Date fechaFin;

    @ManyToOne
    @JoinColumn(name = "id_producto_reciclable", referencedColumnName = "id_producto_reciclable", nullable = false)
    private ProductoReciclableEntity prodReciclableByIdProdReciclable;

    @ManyToOne
    @JoinColumn(name = "id_color", referencedColumnName = "id_color", nullable = false)
    private ColorEntity colorByIdColor;

}
