package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "arq_propiedad")
public class ArqPropiedadEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_propiedad", nullable = false)
    private Long idArqPropiedad;
    @Basic
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Basic
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;
    @Basic
    @Column(name = "activo", nullable = false)
    private Boolean activo;
    @Basic
    @Column(name = "codigo", nullable = false, length = 500)
    private String codigo;
    @Basic
    @Column(name = "valor", nullable = false, length = 500)
    private String valor;
    @Basic
    @Column(name = "grupo_codigo", nullable = false, length = 100)
    private String grupoCodigo;
    @OneToMany(mappedBy = "arqPropiedadByIdArqPropiedad")
    private Collection<ArqAplicacionConfiguracionEntity> arqAplicacionConfiguracionsByIdArqPropiedad;

    public Long getIdArqPropiedad() {
        return idArqPropiedad;
    }

    public void setIdArqPropiedad(Long idArqPropiedad) {
        this.idArqPropiedad = idArqPropiedad;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getGrupoCodigo() {
        return grupoCodigo;
    }

    public void setGrupoCodigo(String grupoCodigo) {
        this.grupoCodigo = grupoCodigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArqPropiedadEntity that = (ArqPropiedadEntity) o;
        return Objects.equals(idArqPropiedad, that.idArqPropiedad) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(activo, that.activo) && Objects.equals(codigo, that.codigo) && Objects.equals(valor, that.valor) && Objects.equals(grupoCodigo, that.grupoCodigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqPropiedad, nombre, descripcion, activo, codigo, valor, grupoCodigo);
    }

    public Collection<ArqAplicacionConfiguracionEntity> getArqAplicacionConfiguracionsByIdArqPropiedad() {
        return arqAplicacionConfiguracionsByIdArqPropiedad;
    }

    public void setArqAplicacionConfiguracionsByIdArqPropiedad(Collection<ArqAplicacionConfiguracionEntity> arqAplicacionConfiguracionsByIdArqPropiedad) {
        this.arqAplicacionConfiguracionsByIdArqPropiedad = arqAplicacionConfiguracionsByIdArqPropiedad;
    }
}
