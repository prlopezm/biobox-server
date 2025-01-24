package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "cuponerapp")
public class CuponerappEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "id_promocion", nullable = false)
    private Integer idPromocion;

    @NotNull
    @Column(name = "promocion", nullable = false, length = Integer.MAX_VALUE)
    private String promocion;

    @NotNull
    @Column(name = "id_marca", nullable = false)
    private Integer idMarca;

    @NotNull
    @Column(name = "puntos", nullable = false)
    private Integer puntos;

}
