package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "arq_rol_permiso_servicio")
public class ArqRolPermisoServicioEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_rol_permiso_servicio", nullable = false)
    private Long idArqRolPermisoServicio;
    @Basic
    @Column(name = "nombre", nullable = true, length = 50)
    private String nombre;
    @Basic
    @Column(name = "id_arq_rol", nullable = false, insertable = false, updatable = false)
    private Long idArqRol;
    @Basic
    @Column(name = "id_arq_servicio_rest", nullable = false, insertable = false, updatable = false)
    private Long idArqServicioRest;
    @Basic
    @Column(name = "activo", nullable = false)
    private Boolean activo;
    @ManyToOne
    @JoinColumn(name = "id_arq_rol", referencedColumnName = "id_arq_rol", nullable = false)
    private ArqRolEntity arqRolByIdArqRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_arq_servicio_rest")
    private ArqServicioRestEntity arqServicioRestByIdArqServicioRest;

    public ArqServicioRestEntity getArqServicioRestByIdArqServicioRest() {
        return arqServicioRestByIdArqServicioRest;
    }

    public void setArqServicioRestByIdArqServicioRest(ArqServicioRestEntity arqServicioRestByIdArqServicioRest) {
        this.arqServicioRestByIdArqServicioRest = arqServicioRestByIdArqServicioRest;
    }

    public Long getIdArqRolPermisoServicio() {
        return idArqRolPermisoServicio;
    }

    public void setIdArqRolPermisoServicio(Long idArqRolPermisoServicio) {
        this.idArqRolPermisoServicio = idArqRolPermisoServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdArqRol() {
        return idArqRol;
    }

    public void setIdArqRol(Long idArqRol) {
        this.idArqRol = idArqRol;
    }

    public Long getIdArqServicioRest() {
        return idArqServicioRest;
    }

    public void setIdArqServicioRest(Long idArqServicioRest) {
        this.idArqServicioRest = idArqServicioRest;
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
        ArqRolPermisoServicioEntity that = (ArqRolPermisoServicioEntity) o;
        return Objects.equals(idArqRolPermisoServicio, that.idArqRolPermisoServicio) && Objects.equals(nombre, that.nombre) && Objects.equals(idArqRol, that.idArqRol) && Objects.equals(idArqServicioRest, that.idArqServicioRest) && Objects.equals(activo, that.activo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqRolPermisoServicio, nombre, idArqRol, idArqServicioRest, activo);
    }

    public ArqRolEntity getArqRolByIdArqRol() {
        return arqRolByIdArqRol;
    }

    public void setArqRolByIdArqRol(ArqRolEntity arqRolByIdArqRol) {
        this.arqRolByIdArqRol = arqRolByIdArqRol;
    }
}
