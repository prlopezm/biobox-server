package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "programa_prontipago", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ProgramaProntipagoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_programa_prontipago")
    private Long idProgramaProntipago;
    @Basic
    @Column(name = "codigo")
    private String codigo;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "vigencia_inicio")
    private Timestamp vigenciaInicio;
    @Basic
    @Column(name = "vigencia_fin")
    private Timestamp vigenciaFin;
    @Basic
    @Column(name = "id_categoria_prontipago", insertable = false, updatable = false)
    private Long idCategoriaProntipago;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria_prontipago", referencedColumnName = "id_categoria_prontipago", nullable = false)
    private ProgramaCategoriaProntipagoEntity categoriaProntipago;
    @OneToMany(fetch = FetchType.LAZY,  mappedBy = "programaProntipago")
    private List<ServicioEntity> puntosColor;
    @OneToOne(fetch = FetchType.LAZY,  mappedBy = "programaProntipago")
    private ProgramaPuntosRequeridosEntity puntosRequeridos;
    
}
