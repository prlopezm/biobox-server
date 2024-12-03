package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "requisito_grado", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class RequisitoGradoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_requisito_grado")
    private Long idRequisitoGrado;
    @Basic
    @Column(name = "id_color")
    private Integer idColor;
    @Basic
    @Column(name = "id_grado_jerarquico")
    private Long idGradoJerarquico;
    @Basic
    @Column(name = "puntos")
    private Integer puntos;
    @Basic
    @Column(name = "fecha_inicio")
    private Timestamp fechaInicio;
    @Basic
    @Column(name = "fecha_fin")
    private Timestamp fechaFin;

}
