package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "material", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class MaterialEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_material")
    private Integer idMaterial;
    @Basic
    @Column(name = "nombre")
    private String nombre;
}
