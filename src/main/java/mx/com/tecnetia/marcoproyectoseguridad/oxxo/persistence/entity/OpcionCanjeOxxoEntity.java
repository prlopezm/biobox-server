package mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "opcion_canje_oxxo")
public class OpcionCanjeOxxoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "nombre", nullable = false, length = Integer.MAX_VALUE)
    private String nombre;

    @NotNull
    @Column(name = "puntos_canjear", nullable = false)
    private Integer puntosCanjear;

}
