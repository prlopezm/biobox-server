package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comodin", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ComodinEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_comodin")
    private Long idComodin;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "id_color")
    private Integer idColor;
    @Basic
    @Column(name = "puntos_necesarios")
    private Integer puntosNecesarios;
}
