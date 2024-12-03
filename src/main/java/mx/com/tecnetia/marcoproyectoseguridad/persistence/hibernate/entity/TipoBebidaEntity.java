package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipo_bebida", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class TipoBebidaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_tipo_bebida")
    private Integer idTipoBebida;
    @Basic
    @Column(name = "nombre")
    private String nombre;

}
