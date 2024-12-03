package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "programa_tipo_descuento", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ProgramaTipoDescuentoEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_programa_tipo_descuento")
    private Long idProgramaTipoDescuento;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    
}
