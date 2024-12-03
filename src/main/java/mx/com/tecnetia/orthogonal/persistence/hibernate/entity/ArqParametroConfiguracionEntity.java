package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "arq_parametro_configuracion")
public class ArqParametroConfiguracionEntity {
    @Basic
    @Column(name = "clave", nullable = false, length = 50)
    private String clave;
    @Basic
    @Column(name = "valor", nullable = false, length = 1000)
    private String valor;
    @Basic
    @Column(name = "descripcion", nullable = true, length = 500)
    private String descripcion;
    @Basic
    @Column(name = "tipo", nullable = true, length = 50)
    private String tipo;
    @Basic
    @Column(name = "activo", nullable = false)
    private Boolean activo;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_parametro_configuracion", nullable = false)
    private Long idArqParametroConfiguracion;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getIdArqParametroConfiguracion() {
        return idArqParametroConfiguracion;
    }

    public void setIdArqParametroConfiguracion(Long idArqParametroConfiguracion) {
        this.idArqParametroConfiguracion = idArqParametroConfiguracion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArqParametroConfiguracionEntity that = (ArqParametroConfiguracionEntity) o;
        return Objects.equals(clave, that.clave) && Objects.equals(valor, that.valor) && Objects.equals(descripcion, that.descripcion) && Objects.equals(tipo, that.tipo) && Objects.equals(activo, that.activo) && Objects.equals(idArqParametroConfiguracion, that.idArqParametroConfiguracion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clave, valor, descripcion, tipo, activo, idArqParametroConfiguracion);
    }
}
