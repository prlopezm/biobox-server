package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "marca", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class MarcaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_marca")
    private Integer idMarca;
    @Basic
    @Column(name = "nombre")
    private String nombre;


}
