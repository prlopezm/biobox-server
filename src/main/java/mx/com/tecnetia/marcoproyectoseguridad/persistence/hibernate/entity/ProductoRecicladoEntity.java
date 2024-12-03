package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "producto_reciclado", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ProductoRecicladoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_producto_reciclado")
    private Long idProductoReciclado;
    @Basic
    @Column(name = "momento_reciclaje")
    private Timestamp momentoReciclaje;
    @Basic
    @Column(name = "id_producto_reciclable", nullable = false, insertable = false, updatable = false)
    private Long idProductoReciclable;
    @Basic
    @Column(name = "id_arq_usuario", nullable = false, insertable = false, updatable = false)
    private Long idArqUsuario;
    @Basic
    @Column(name = "exitoso")
    private Boolean exitoso;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto_reciclable", referencedColumnName = "id_producto_reciclable", nullable = true)
    private ProductoReciclableEntity productoReciclableByIdProductoReciclable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_arq_usuario", referencedColumnName = "id_arq_usuario", nullable = false)
    private ArqUsuarioEntity arqUsuarioByIdArqUsuario;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productoRecicladoByIdProductoReciclado")
    private Set<UsuarioPuntosColorAcumuladoEntity> usuarioPuntosColorAcumulados;

 }
