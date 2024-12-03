package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "conversion", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ConversionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_conversion")
    private Long idServicio;
    @Basic
    @Column(name = "id_color", insertable = false, updatable = false)
    private Integer idColor;
    @Basic
    @Column(name = "puntos")
    private Integer puntos;
    @Basic
    @Column(name = "monto_centavos")
    private Integer montoCentavos;
    @Basic
    @Column(name = "fecha_inicio")
    private Timestamp fechaInicio;
    @Basic
    @Column(name = "fecha_fin")
    private Timestamp fechaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color", referencedColumnName = "id_color", nullable = false)
    private ColorEntity colorByIdColor;

}
