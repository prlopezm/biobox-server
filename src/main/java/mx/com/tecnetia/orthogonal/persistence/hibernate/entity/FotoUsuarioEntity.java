package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "foto_usuario", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class FotoUsuarioEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_foto_usuario")
    private Long idFotoUsuario;
    @Basic
    @Column(name = "nombre_foto")
    private String nombreFoto;
    @Basic
    @Column(name = "foto")
    private byte[] foto;
    @Basic
    @Column(name = "id_arq_usuario", insertable = false, updatable = false)
    private Long idArqUsuario;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_arq_usuario")
    private ArqUsuarioEntity arqUsuarioEntity;
}
