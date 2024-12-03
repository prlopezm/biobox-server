package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;

@Entity
@Table(name = "usuario_puntos", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class UsuarioPuntosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_usuario_puntos")
    private Long idUsuarioPuntos;
    @Basic
    @Column(name = "id_arq_usuario", insertable = false, updatable = false)
    private Long idArqUsuario;
    @Basic
    @Column(name = "puntos")
    private Integer puntos;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_arq_usuario")
    private ArqUsuarioEntity arqUsuarioEntity;

}
