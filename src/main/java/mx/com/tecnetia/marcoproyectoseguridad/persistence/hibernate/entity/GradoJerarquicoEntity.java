package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "grado_jerarquico", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class GradoJerarquicoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_grado_jerarquico")
    private Long idGradoJerarquico;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "anterior")
    private Long anterior;
    @Basic
    @Column(name = "imagen")
    private byte[] imagen;

}
