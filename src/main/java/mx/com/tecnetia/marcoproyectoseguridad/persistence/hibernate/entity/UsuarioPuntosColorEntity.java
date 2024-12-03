package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "usuario_puntos_color", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class UsuarioPuntosColorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_usuario_puntos_color")
    private Long idUsuarioPuntosColor;
    @Basic
    @Column(name = "id_arq_usuario", insertable = false, updatable = false)
    private Long idArqUsuario;
    @Basic
    @Column(name = "puntos")
    private Integer puntos;
    @Basic
    @Column(name = "id_color", insertable = false, updatable = false)
    private Integer idColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color", referencedColumnName = "id_color", nullable = false)
    private ColorEntity colorByIdColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_arq_usuario", referencedColumnName = "id_arq_usuario", nullable = false)
    private ArqUsuarioEntity arqUsuarioByIdArqUsuario;
}
