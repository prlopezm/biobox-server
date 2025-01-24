package mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "denominacion_recarga_cel_usada")
public class DenominacionRecargaCelUsadaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "denominacion_recarga_cel_id", nullable = false)
    private DenominacionRecargaCelEntity denominacionRecargaCel;

    @NotNull
    @Column(name = "arq_usuario_id", nullable = false)
    private Long arqUsuarioId;

    @NotNull
    @Column(name = "momento", nullable = false)
    private LocalDateTime momento = LocalDateTime.now();

    @NotNull
    @Column(name = "puntos", nullable = false)
    private Integer puntos;

    @NotNull
    @Column(name = "cel", nullable = false)
    private String cel;

}
