package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "programa_lealtad", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ProgramaLealtadEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_programa_lealtad")
    private Long idProgramaLealtad;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "contacto")
    private String contacto;
    @Basic
    @Column(name = "codigo")
    private String codigo;

}
