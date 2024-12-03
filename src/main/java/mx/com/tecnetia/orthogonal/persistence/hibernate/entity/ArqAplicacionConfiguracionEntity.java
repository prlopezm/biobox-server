package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "arq_aplicacion_configuracion")
public class ArqAplicacionConfiguracionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_aplicacion_configuracion", nullable = false)
    private Long idArqAplicacionConfiguracion;
    @Basic
    @Column(name = "id_arq_propiedad", nullable = false, insertable = false, updatable = false)
    private Long idArqPropiedad;
    @Basic
    @Column(name = "id_arq_aplicacion", nullable = false, insertable = false, updatable = false)
    private Long idArqAplicacion;
    @Basic
    @Column(name = "valor", nullable = false, length = 5000)
    private String valor;
    @Basic
    @Column(name = "activo", nullable = true)
    private Boolean activo;
    @Basic
    @Column(name = "codigo", nullable = false, length = 100)
    private String codigo;
    @ManyToOne
    @JoinColumn(name = "id_arq_aplicacion", referencedColumnName = "id_arq_aplicacion", nullable = false)
    private ArqAplicacionEntity arqAplicacionByIdArqAplicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_arq_propiedad")
    private ArqPropiedadEntity arqPropiedadByIdArqPropiedad;

    public ArqPropiedadEntity getArqPropiedadByIdArqPropiedad() {
        return arqPropiedadByIdArqPropiedad;
    }

    public void setArqPropiedadByIdArqPropiedad(ArqPropiedadEntity arqPropiedadByIdArqPropiedad) {
        this.arqPropiedadByIdArqPropiedad = arqPropiedadByIdArqPropiedad;
    }

    public Long getIdArqAplicacionConfiguracion() {
        return idArqAplicacionConfiguracion;
    }

    public void setIdArqAplicacionConfiguracion(Long idArqAplicacionConfiguracion) {
        this.idArqAplicacionConfiguracion = idArqAplicacionConfiguracion;
    }

    public Long getIdArqPropiedad() {
        return idArqPropiedad;
    }

    public void setIdArqPropiedad(Long idArqPropiedad) {
        this.idArqPropiedad = idArqPropiedad;
    }

    public Long getIdArqAplicacion() {
        return idArqAplicacion;
    }

    public void setIdArqAplicacion(Long idArqAplicacion) {
        this.idArqAplicacion = idArqAplicacion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
        ArqAplicacionConfiguracionEntity that = (ArqAplicacionConfiguracionEntity) o;
        return Objects.equals(idArqAplicacionConfiguracion, that.idArqAplicacionConfiguracion) && Objects.equals(idArqPropiedad, that.idArqPropiedad) && Objects.equals(idArqAplicacion, that.idArqAplicacion) && Objects.equals(valor, that.valor) && Objects.equals(activo, that.activo) && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqAplicacionConfiguracion, idArqPropiedad, idArqAplicacion, valor, activo, codigo);
    }

    public ArqAplicacionEntity getArqAplicacionByIdArqAplicacion() {
        return arqAplicacionByIdArqAplicacion;
    }

    public void setArqAplicacionByIdArqAplicacion(ArqAplicacionEntity arqAplicacionByIdArqAplicacion) {
        this.arqAplicacionByIdArqAplicacion = arqAplicacionByIdArqAplicacion;
    }
}
