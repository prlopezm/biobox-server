package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "capacidad", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class CapacidadEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_capacidad")
    private Long idCapacidad;
    @Basic
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @Basic
    @Column(name = "id_unidad_medida", insertable = false, updatable = false)
    private Integer idUnidadMedida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidad_medida", referencedColumnName = "id_unidad_medida", nullable = false)
    private UnidadMedidaEntity unidadMedidaByIdUnidadMedida;
  }
