package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "foto_producto_reciclado", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class FotoProductoRecicladoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_foto_producto_reciclado")
    private Long idFotoProductoReciclado;
    @Basic
    @Column(name = "id_producto_reciclado", nullable = false, insertable = false, updatable = false)
    private Long idProductoReciclado;
    @Basic
    @Column(name = "foto")
    private byte[] foto;
    @Basic
    @Column(name = "exitoso")
    private Boolean exitoso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto_reciclado", referencedColumnName = "id_producto_reciclado", nullable = false)
    private ProductoRecicladoEntity productoRecicladoByIdProductoReciclado;
}
