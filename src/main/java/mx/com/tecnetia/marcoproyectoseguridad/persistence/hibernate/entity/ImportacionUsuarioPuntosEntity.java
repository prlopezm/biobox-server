package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "importacion_usuario_puntos", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ImportacionUsuarioPuntosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_importacion_usuario_puntos")
    private Long idImportacionUsuarioPuntos;
    @Basic
    @Column(name = "id_arq_usuario")
    private Long idArqUsuario;
    @Basic
    @Column(name = "puntos")
    private Integer puntos;
    @Basic
    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

}
