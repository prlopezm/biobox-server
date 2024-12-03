package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "arq_servicio_rest")
public class ArqServicioRestEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_servicio_rest", nullable = false)
    private Long idArqServicioRest;
    @Basic
    @Column(name = "nombre", nullable = true, length = 50)
    private String nombre;
    @Basic
    @Column(name = "descripcion", nullable = true, length = 500)
    private String descripcion;
    @Basic
    @Column(name = "activo", nullable = false)
    private Boolean activo;
    @Basic
    @Column(name = "uri", nullable = false, length = 500)
    private String uri;
    @Basic
    @Column(name = "codigo", nullable = false, length = 100)
    private String codigo;
    @Basic
    @Column(name = "codigo_metodo_servicio", nullable = false, length = 100, insertable = false, updatable = false)
    private String codigoMetodoServicio;
    @Basic
    @Column(name = "id_arq_modulo", nullable = false, insertable = false, updatable = false)
    private Long idArqModulo;
    @Basic
    @Column(name = "http_status", nullable = true, length = 500)
    private String httpStatus;
    @Basic
    @Column(name = "retorno", nullable = true, length = 200)
    private String retorno;
    @OneToMany(mappedBy = "arqServicioRestByIdArqServicioRest")
    private Collection<ArqParametroServicioRestEntity> arqParametroServicioRestsByIdArqServicioRest;
    @OneToMany(mappedBy = "arqServicioRestByIdArqServicioRest")
    private Collection<ArqRolPermisoServicioEntity> arqRolPermisoServiciosByIdArqServicioRest;
    @ManyToOne
    @JoinColumn(name = "codigo_metodo_servicio", referencedColumnName = "codigo_metodo_servicio", nullable = false)
    private ArqMetodoServicioEntity arqMetodoServicioByCodigoMetodoServicio;
    @ManyToOne
    @JoinColumn(name = "id_arq_modulo", referencedColumnName = "id_arq_modulo", nullable = false)
    private ArqModuloEntity arqModuloByIdArqModulo;

    public Long getIdArqServicioRest() {
        return idArqServicioRest;
    }

    public void setIdArqServicioRest(Long idArqServicioRest) {
        this.idArqServicioRest = idArqServicioRest;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoMetodoServicio() {
        return codigoMetodoServicio;
    }

    public void setCodigoMetodoServicio(String codigoMetodoServicio) {
        this.codigoMetodoServicio = codigoMetodoServicio;
    }

    public Long getIdArqModulo() {
        return idArqModulo;
    }

    public void setIdArqModulo(Long idArqModulo) {
        this.idArqModulo = idArqModulo;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArqServicioRestEntity that = (ArqServicioRestEntity) o;
        return Objects.equals(idArqServicioRest, that.idArqServicioRest) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(activo, that.activo) && Objects.equals(uri, that.uri) && Objects.equals(codigo, that.codigo) && Objects.equals(codigoMetodoServicio, that.codigoMetodoServicio) && Objects.equals(idArqModulo, that.idArqModulo) && Objects.equals(httpStatus, that.httpStatus) && Objects.equals(retorno, that.retorno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqServicioRest, nombre, descripcion, activo, uri, codigo, codigoMetodoServicio, idArqModulo, httpStatus, retorno);
    }

    public Collection<ArqParametroServicioRestEntity> getArqParametroServicioRestsByIdArqServicioRest() {
        return arqParametroServicioRestsByIdArqServicioRest;
    }

    public void setArqParametroServicioRestsByIdArqServicioRest(Collection<ArqParametroServicioRestEntity> arqParametroServicioRestsByIdArqServicioRest) {
        this.arqParametroServicioRestsByIdArqServicioRest = arqParametroServicioRestsByIdArqServicioRest;
    }

    public Collection<ArqRolPermisoServicioEntity> getArqRolPermisoServiciosByIdArqServicioRest() {
        return arqRolPermisoServiciosByIdArqServicioRest;
    }

    public void setArqRolPermisoServiciosByIdArqServicioRest(Collection<ArqRolPermisoServicioEntity> arqRolPermisoServiciosByIdArqServicioRest) {
        this.arqRolPermisoServiciosByIdArqServicioRest = arqRolPermisoServiciosByIdArqServicioRest;
    }

    public ArqMetodoServicioEntity getArqMetodoServicioByCodigoMetodoServicio() {
        return arqMetodoServicioByCodigoMetodoServicio;
    }

    public void setArqMetodoServicioByCodigoMetodoServicio(ArqMetodoServicioEntity arqMetodoServicioByCodigoMetodoServicio) {
        this.arqMetodoServicioByCodigoMetodoServicio = arqMetodoServicioByCodigoMetodoServicio;
    }

    public ArqModuloEntity getArqModuloByIdArqModulo() {
        return arqModuloByIdArqModulo;
    }

    public void setArqModuloByIdArqModulo(ArqModuloEntity arqModuloByIdArqModulo) {
        this.arqModuloByIdArqModulo = arqModuloByIdArqModulo;
    }
}
