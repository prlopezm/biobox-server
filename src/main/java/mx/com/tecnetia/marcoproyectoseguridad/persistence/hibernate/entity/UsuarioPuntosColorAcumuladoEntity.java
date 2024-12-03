package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario_puntos_color_acumulado", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class UsuarioPuntosColorAcumuladoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_usuario_puntos_color_acumulado")
    private Long idUsuarioPuntosColorAcumulado;
    @Basic
    @Column(name = "puntos")
    private Integer puntos;
    @Basic
    @Column(name = "id_color", insertable = false, updatable = false)
    private Integer idColor;

    @Basic
    @Column(name = "id_producto_reciclado", nullable = false, insertable = false, updatable = false)
    private Long idProductoReciclado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color", referencedColumnName = "id_color", nullable = false)
    private ColorEntity colorByIdColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto_reciclado", referencedColumnName = "id_producto_reciclado", nullable = false)
    private ProductoRecicladoEntity productoRecicladoByIdProductoReciclado;

}
