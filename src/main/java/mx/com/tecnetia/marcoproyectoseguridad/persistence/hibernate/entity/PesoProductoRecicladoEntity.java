package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "peso_producto_reciclado", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class PesoProductoRecicladoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_peso_producto_reciclado")
    private Long idPesoProductoReciclado;
    @Basic
    @Column(name = "id_producto_reciclado", nullable = false, insertable = false, updatable = false)
    private Long idProductoReciclado;
    @Basic
    @Column(name = "peso")
    private Integer peso;
    @Basic
    @Column(name = "exitoso")
    private Boolean exitoso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto_reciclado", referencedColumnName = "id_producto_reciclado", nullable = false)
    private ProductoRecicladoEntity productoRecicladoByIdProductoReciclado;
}
