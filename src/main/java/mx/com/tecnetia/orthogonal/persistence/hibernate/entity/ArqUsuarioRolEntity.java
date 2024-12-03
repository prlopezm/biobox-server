package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "arq_usuario_rol")
public class ArqUsuarioRolEntity {
    @Basic
    @Column(name = "id_arq_usuario", nullable = false, insertable = false, updatable = false)
    private Long idArqUsuario;
    @Basic
    @Column(name = "id_arq_rol", nullable = false, insertable = false, updatable = false)
    private Long idArqRol;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_usuario_rol", nullable = false)
    private Long idArqUsuarioRol;
    @ManyToOne
    @JoinColumn(name = "id_arq_usuario", referencedColumnName = "id_arq_usuario", nullable = false)
    private ArqUsuarioEntity arqUsuarioByIdArqUsuario;
    @ManyToOne
    @JoinColumn(name = "id_arq_rol", referencedColumnName = "id_arq_rol", nullable = false)
    private ArqRolEntity arqRolByIdArqRol;

    public Long getIdArqUsuario() {
        return idArqUsuario;
    }

    public void setIdArqUsuario(Long idArqUsuario) {
        this.idArqUsuario = idArqUsuario;
    }

    public Long getIdArqRol() {
        return idArqRol;
    }

    public void setIdArqRol(Long idArqRol) {
        this.idArqRol = idArqRol;
    }

    public Long getIdArqUsuarioRol() {
        return idArqUsuarioRol;
    }

    public void setIdArqUsuarioRol(Long idArqUsuarioRol) {
        this.idArqUsuarioRol = idArqUsuarioRol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArqUsuarioRolEntity that = (ArqUsuarioRolEntity) o;
        return Objects.equals(idArqUsuario, that.idArqUsuario) && Objects.equals(idArqRol, that.idArqRol) && Objects.equals(idArqUsuarioRol, that.idArqUsuarioRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqUsuario, idArqRol, idArqUsuarioRol);
    }

    public ArqUsuarioEntity getArqUsuarioByIdArqUsuario() {
        return arqUsuarioByIdArqUsuario;
    }

    public void setArqUsuarioByIdArqUsuario(ArqUsuarioEntity arqUsuarioByIdArqUsuario) {
        this.arqUsuarioByIdArqUsuario = arqUsuarioByIdArqUsuario;
    }

    public ArqRolEntity getArqRolByIdArqRol() {
        return arqRolByIdArqRol;
    }

    public void setArqRolByIdArqRol(ArqRolEntity arqRolByIdArqRol) {
        this.arqRolByIdArqRol = arqRolByIdArqRol;
    }
}
