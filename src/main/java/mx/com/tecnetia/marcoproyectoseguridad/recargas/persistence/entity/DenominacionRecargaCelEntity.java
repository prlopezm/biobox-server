package mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "denominacion_recarga_cel")
public class DenominacionRecargaCelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "compania_cel_id", nullable = false)
    private CompaniaCelEntity companiaCel;

    @NotNull
    @Column(name = "monto", nullable = false)
    private Integer monto;

    @NotNull
    @Column(name = "puntos", nullable = false)
    private Integer puntos;

}
