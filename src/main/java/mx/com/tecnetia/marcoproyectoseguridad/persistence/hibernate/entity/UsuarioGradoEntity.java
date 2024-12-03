package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "usuario_grado", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class UsuarioGradoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_usuario_grado")
    private Long idUsuarioGrado;
    @Basic
    @Column(name = "id_grado_jerarquico")
    private Long idGradoJerarquico;
    @Basic
    @Column(name = "id_arq_usuario")
    private Long idArqUsuario;
    @Basic
    @Column(name = "fecha_inicio")
    private Timestamp fechaInicio;
    @Basic
    @Column(name = "fecha_fin")
    private Timestamp fechaFin;
}
