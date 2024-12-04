package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "producto_reciclable", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
@NoArgsConstructor
public class ProductoReciclableEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_producto_reciclable")
    private Long idProductoReciclable;
    @Basic
    @Column(name = "sku")
    private String sku;
    @Basic
    @Column(name = "bar_code")
    private String barCode;
    @Basic
    @Column(name = "id_material", insertable = false, updatable = false)
    private Integer idMaterial;
    @Basic
    @Column(name = "id_sub_marca", insertable = false, updatable = false)
    private Integer idSubMarca;
    @Basic
    @Column(name = "id_fabricante", insertable = false, updatable = false)
    private Integer idFabricante;
    @Basic
    @Column(name = "id_capacidad", insertable = false, updatable = false)
    private Long idCapacidad;

    @ManyToOne
    @JoinColumn(name = "id_material", referencedColumnName = "id_material")
    private MaterialEntity materialByIdMaterial;

    @ManyToOne
    @JoinColumn(name = "id_sub_marca", referencedColumnName = "id_sub_marca")
    private SubMarcaEntity subMarcaByIdSubMarca;

    @ManyToOne
    @JoinColumn(name = "id_capacidad", referencedColumnName = "id_capacidad")
    private CapacidadEntity capacidadByIdCapacidad;

    @Basic
    @Column(name = "peso_minimo")
    private BigDecimal pesoMinimo;

    @Basic
    @Column(name = "peso_maximo")
    private BigDecimal pesoMaximo;

    @ManyToOne
    @JoinColumn(name = "id_fabricante", referencedColumnName = "id_fabricante", nullable = false)
    private FabricanteEntity fabricante;
    
    public ProductoReciclableEntity(Long idProductoReciclable) {
    	this.idProductoReciclable = idProductoReciclable;
    }

}
