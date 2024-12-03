package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

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
@Table(name = "sku_prontipago", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class SkuProntipagoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_sku_prontipago")
    private Long idSkuProntipago;
    @Basic
    @Column(name = "id_prontipago", insertable = false, updatable = false)
    private Long idProntipago;
    @Basic
    @Column(name = "sku")
    private String sku;
    @Basic
    @Column(name = "fecha_inicio")
    private Timestamp fechaInicio;
    @Basic
    @Column(name = "fecha_fin")
    private Timestamp fechaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prontipago", referencedColumnName = "id_programa_prontipago", nullable = false)
    private ProgramaProntipagoEntity programaProntipagoByIdProntipago;
}
