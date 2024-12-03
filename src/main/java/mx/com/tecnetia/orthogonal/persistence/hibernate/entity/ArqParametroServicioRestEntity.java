package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "arq_parametro_servicio_rest")
public class ArqParametroServicioRestEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_parametro_servicio_rest", nullable = false)
    private Long idArqParametroServicioRest;
    @Basic
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic
    @Column(name = "parametro", nullable = false, length = 100)
    private String parametro;
    @Basic
    @Column(name = "id_arq_servicio_rest", nullable = false, insertable = false, updatable = false)
    private Long idArqServicioRest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_arq_servicio_rest")
    private ArqServicioRestEntity arqServicioRestByIdArqServicioRest;

    public ArqServicioRestEntity getArqServicioRestByIdArqServicioRest() {
        return arqServicioRestByIdArqServicioRest;
    }

    public void setArqServicioRestByIdArqServicioRest(ArqServicioRestEntity arqServicioRestByIdArqServicioRest) {
        this.arqServicioRestByIdArqServicioRest = arqServicioRestByIdArqServicioRest;
    }

    public Long getIdArqParametroServicioRest() {
        return idArqParametroServicioRest;
    }

    public void setIdArqParametroServicioRest(Long idArqParametroServicioRest) {
        this.idArqParametroServicioRest = idArqParametroServicioRest;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public Long getIdArqServicioRest() {
        return idArqServicioRest;
    }

    public void setIdArqServicioRest(Long idArqServicioRest) {
        this.idArqServicioRest = idArqServicioRest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArqParametroServicioRestEntity that = (ArqParametroServicioRestEntity) o;
        return Objects.equals(idArqParametroServicioRest, that.idArqParametroServicioRest) && Objects.equals(nombre, that.nombre) && Objects.equals(parametro, that.parametro) && Objects.equals(idArqServicioRest, that.idArqServicioRest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqParametroServicioRest, nombre, parametro, idArqServicioRest);
    }
}
