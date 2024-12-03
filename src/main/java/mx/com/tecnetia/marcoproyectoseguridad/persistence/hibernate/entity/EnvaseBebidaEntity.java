package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "envase_bebida", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class EnvaseBebidaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_envase_bebida")
    private Long idEnvaseBebida;
    @Basic
    @Column(name = "id_producto_reciclable")
    private Long idProductoReciclable;
    @Basic
    @Column(name = "id_tipo_bebida")
    private Integer idTipoBebida;
    @Basic
    @Column(name = "id_sabor")
    private Integer idSabor;

}
