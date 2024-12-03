package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

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
@Table(name = "programa_puntos_requeridos", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ProgramaPuntosRequeridosEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_programa_puntos_requeridos")
    private Long idProgramaPuntosRequeridos;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programa_prontipago", referencedColumnName = "id_programa_prontipago", nullable = false)
    private ProgramaProntipagoEntity programaProntipago;
	
    @Basic
    @Column(name = "puntos_limite_inferior")
    private Integer puntosLimiteInferior;
    
    @Basic
    @Column(name = "puntos_limite_superior")
    private Integer puntosLimiteSuperior;
    
    @Basic
    @Column(name = "puntos_coste")
    private Integer puntosCoste;
    
}
