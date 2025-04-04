package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estado")
public class EstadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "id_estado", nullable = false)
    private Integer idEstado;

    @Column(name = "nombre")
    private String nombre;
}
