package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "arq_metodo_servicio")
public class ArqMetodoServicioEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "codigo_metodo_servicio", nullable = false, length = 100)
    private String codigoMetodoServicio;
    @Basic
    @Column(name = "nombre", nullable = true, length = 500)
    private String nombre;
    @Basic
    @Column(name = "descripcion", nullable = true, length = 500)
    private String descripcion;

    public String getCodigoMetodoServicio() {
        return codigoMetodoServicio;
    }

    public void setCodigoMetodoServicio(String codigoMetodoServicio) {
        this.codigoMetodoServicio = codigoMetodoServicio;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArqMetodoServicioEntity that = (ArqMetodoServicioEntity) o;
        return Objects.equals(codigoMetodoServicio, that.codigoMetodoServicio) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoMetodoServicio, nombre, descripcion);
    }
}
