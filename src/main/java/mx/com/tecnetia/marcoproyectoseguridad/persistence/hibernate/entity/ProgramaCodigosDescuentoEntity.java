package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import java.math.BigDecimal;
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
@Table(name = "programa_codigos_descuento", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ProgramaCodigosDescuentoEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_programa_codigos_descuento")
    private Long idProgramaCodigosDescuento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programa_puntos_requeridos", referencedColumnName = "id_programa_puntos_requeridos", nullable = false)
    private ProgramaPuntosRequeridosEntity programaPuntosRequeridos;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programa_tipo_descuento", referencedColumnName = "id_programa_tipo_descuento", nullable = false)
    private ProgramaTipoDescuentoEntity programaTipoDescuento;
    
    @Basic
    @Column(name = "codigo")
    private String codigo;
    
    @Basic
    @Column(name = "fecha_utilizado")
    private Timestamp fechaUtilizado;
    
    @Basic
    @Column(name = "id_arq_usuario")
    private Long idArqUsuario;
    
    @Basic
    @Column(name = "imagen")
    private String imagen;
    
    @Basic
    @Column(name = "valor_n", insertable = false, updatable = false)
    private BigDecimal valorN;
    
    @Basic
    @Column(name = "valor_x", insertable = false, updatable = false)
    private BigDecimal valorX;
    
}
