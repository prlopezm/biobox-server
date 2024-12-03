package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "unidad_medida", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class UnidadMedidaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_unidad_medida")
    private Integer idUnidadMedida;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "siglas")
    private String siglas;

}
