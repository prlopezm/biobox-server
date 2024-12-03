package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "equivalencia_puntos", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class EquivalenciaPuntosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_equivalencia_puntos")
    private Long idEquivalenciaPuntos;
    @Basic
    @Column(name = "id_programa_lealtad", insertable = false, updatable = false)
    private Long idProgramaLealtad;
    @Basic
    @Column(name = "puntos")
    private Integer puntos;
    @Basic
    @Column(name = "monto")
    private BigDecimal monto;
    @Basic
    @Column(name = "monto_unidad_canje")
    private BigDecimal montoUnidadCanje;
    @Basic
    @Column(name = "fecha_desactivacion")
    private Timestamp fechaDesactivacion;
    @Basic
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programa_lealtad", referencedColumnName = "id_programa_lealtad", nullable = false)
    private ProgramaLealtadEntity programaLealtadByIdProgramaLealtad;

}
