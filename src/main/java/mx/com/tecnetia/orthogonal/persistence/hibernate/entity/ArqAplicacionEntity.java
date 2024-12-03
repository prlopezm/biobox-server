package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "arq_aplicacion")
public class ArqAplicacionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_aplicacion", nullable = false)
    private Long idArqAplicacion;
    @Basic
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Basic
    @Column(name = "descripcion", nullable = true, length = 500)
    private String descripcion;
    @Basic
    @Column(name = "id_arq_cliente", nullable = false, insertable = false, updatable = false)
    private Long idArqCliente;
    @Basic
    @Column(name = "activo", nullable = false, insertable = false, updatable = false)
    private Boolean activo;
    @Basic
    @Column(name = "uri", nullable = true, length = 500)
    private String uri;
    @Basic
    @Column(name = "codigo", nullable = false, length = 100)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_arq_cliente")
    private ArqClienteEntity arqClienteByIdArqCliente;

    public ArqClienteEntity getArqClienteByIdArqCliente() {
        return arqClienteByIdArqCliente;
    }

    public void setArqClienteByIdArqCliente(ArqClienteEntity arqClienteByIdArqCliente) {
        this.arqClienteByIdArqCliente = arqClienteByIdArqCliente;
    }

    public Long getIdArqAplicacion() {
        return idArqAplicacion;
    }

    public void setIdArqAplicacion(Long idArqAplicacion) {
        this.idArqAplicacion = idArqAplicacion;
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

    public Long getIdArqCliente() {
        return idArqCliente;
    }

    public void setIdArqCliente(Long idArqCliente) {
        this.idArqCliente = idArqCliente;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArqAplicacionEntity that = (ArqAplicacionEntity) o;
        return Objects.equals(idArqAplicacion, that.idArqAplicacion) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(idArqCliente, that.idArqCliente) && Objects.equals(activo, that.activo) && Objects.equals(uri, that.uri) && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqAplicacion, nombre, descripcion, idArqCliente, activo, uri, codigo);
    }
}
