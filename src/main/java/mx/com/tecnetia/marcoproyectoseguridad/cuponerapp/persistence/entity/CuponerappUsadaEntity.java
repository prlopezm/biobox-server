package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cuponerapp_usada")
public class CuponerappUsadaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cuponerapp_id", nullable = false)
    private CuponerappEntity cuponUsado;

    @NotNull
    @Column(name = "arq_usuario_id", nullable = false)
    private Long arqUsuarioId;

    @NotNull
    @Column(name = "puntos", nullable = false)
    private Integer puntos;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "momento", nullable = false)
    private LocalDateTime momento = LocalDateTime.now();

    @Column(name = "folio")
    private String folio;

    @Column(name = "fecha", length = Integer.MAX_VALUE)
    private String fecha;

    @Column(name = "hora", length = Integer.MAX_VALUE)
    private String hora;

    @Column(name = "codigo_qr", length = Integer.MAX_VALUE)
    private String codigoQr;

    @Column(name = "imagen_base64", length = Integer.MAX_VALUE)
    private String imagenBase64;

    @Column(name = "costo", length = Integer.MAX_VALUE)
    private String costo;

    @Column(name = "nombre", length = Integer.MAX_VALUE)
    private String nombre;

}
