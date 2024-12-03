package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sabor", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class SaborEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_sabor")
    private Integer idSabor;
    @Basic
    @Column(name = "nombre")
    private String nombre;
}
