package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "producto_reciclado_quiosco", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ProductoRecicladoQuioscoEntity {
    @Id
    @Column(name = "id_producto_reciclado")
    private Long idProductoReciclado;
    @Basic
    @Column(name = "id_quiosco")
    private Long idQuiosco;

}
