package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fabricante", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class FabricanteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_fabricante")
    private Integer idFabricante;
    @Basic
    @Column(name = "nombre")
    private String nombre;

}
