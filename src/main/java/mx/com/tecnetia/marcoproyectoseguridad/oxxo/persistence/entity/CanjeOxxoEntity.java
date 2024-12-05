package mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "canje_oxxo")
public class CanjeOxxoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "momento", nullable = false)
    private Instant momento;

    @NotNull
    @Column(name = "arq_usuario_id", nullable = false)
    private Long arqUsuarioId;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "exitoso", nullable = false)
    private Boolean exitoso = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "opcion_canje_oxxo_id", nullable = false)
    private OpcionCanjeOxxoEntity opcionCanjeOxxo;

    @Column(name = "respuesta_oxxo", length = Integer.MAX_VALUE)
    private String respuestaOxxo;

}
