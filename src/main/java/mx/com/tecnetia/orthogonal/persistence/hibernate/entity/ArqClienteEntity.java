package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "arq_cliente")
public class ArqClienteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_cliente", nullable = false)
    private Long idArqCliente;
    @Basic
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Basic
    @Column(name = "direccion", nullable = true, length = 500)
    private String direccion;
    @Basic
    @Column(name = "descripcion", nullable = true, length = 500)
    private String descripcion;
    @Basic
    @Column(name = "activo", nullable = true)
    private Boolean activo;
    @Basic
    @Column(name = "codigo", nullable = false, length = 100)
    private String codigo;
    @OneToMany(mappedBy = "arqClienteByIdArqCliente")
    private Collection<ArqAplicacionEntity> arqAplicacionsByIdArqCliente;

    public Long getIdArqCliente() {
        return idArqCliente;
    }

    public void setIdArqCliente(Long idArqCliente) {
        this.idArqCliente = idArqCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
        ArqClienteEntity that = (ArqClienteEntity) o;
        return Objects.equals(idArqCliente, that.idArqCliente) && Objects.equals(nombre, that.nombre) && Objects.equals(direccion, that.direccion) && Objects.equals(descripcion, that.descripcion) && Objects.equals(activo, that.activo) && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqCliente, nombre, direccion, descripcion, activo, codigo);
    }

    public Collection<ArqAplicacionEntity> getArqAplicacionsByIdArqCliente() {
        return arqAplicacionsByIdArqCliente;
    }

    public void setArqAplicacionsByIdArqCliente(Collection<ArqAplicacionEntity> arqAplicacionsByIdArqCliente) {
        this.arqAplicacionsByIdArqCliente = arqAplicacionsByIdArqCliente;
    }
}
