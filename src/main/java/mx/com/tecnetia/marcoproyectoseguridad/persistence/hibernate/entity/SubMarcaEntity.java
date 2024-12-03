package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sub_marca", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class SubMarcaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_sub_marca")
    private Integer idSubMarca;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "id_marca", insertable = false, updatable = false)
    private Integer idMarca;

    @ManyToOne
    @JoinColumn(name = "id_marca", referencedColumnName = "id_marca", nullable = false)
    private MarcaEntity marcaByIdMarca;

}
