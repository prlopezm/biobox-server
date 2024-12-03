package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "arq_rol")
public class ArqRolEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_rol", nullable = false)
    private Long idArqRol;
    @Basic
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Basic
    @Column(name = "descripcion", nullable = true, length = 500)
    private String descripcion;
    @Basic
    @Column(name = "activo", nullable = true)
    private Boolean activo;

    public Long getIdArqRol() {
        return idArqRol;
    }

    public void setIdArqRol(Long idArqRol) {
        this.idArqRol = idArqRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArqRolEntity that = (ArqRolEntity) o;
        return Objects.equals(idArqRol, that.idArqRol) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(activo, that.activo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqRol, nombre, descripcion, activo);
    }
}
