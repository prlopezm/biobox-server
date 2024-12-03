package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categoria_prontipago", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ProgramaCategoriaProntipagoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_categoria_prontipago")
    private Long idCategoriaProntipago;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "contacto")
    private String contacto;
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "color_fondo")
    private String colorFondo;
    @Basic
    @Column(name = "tipo_programa")
    private String tipoPrograma;
    @Basic
    @Column(name = "url_logo")
    private String urlLogo;
    @Basic
    @Column(name = "url_logo_2")
    private String urlLogo2;
    @Basic
    @Column(name = "url_portal")
    private String urlPortal;
    @Basic
    @Column(name = "canje_unico")
    private boolean canjeUnico;
    @Basic
    @Column(name = "vigencia_inicio")
    private Timestamp vigenciaInicio;
    @Basic
    @Column(name = "vigencia_fin")
    private Timestamp vigenciaFin;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoriaProntipago")
    private List<ProgramaProntipagoEntity> programasProntipagos;
}
