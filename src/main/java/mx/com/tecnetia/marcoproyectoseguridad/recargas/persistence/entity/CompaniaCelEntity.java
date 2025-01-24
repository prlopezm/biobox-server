package mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "compania_cel")
public class CompaniaCelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "nombre", nullable = false, length = Integer.MAX_VALUE)
    private String nombre;

    @NotNull
    @Column(name = "id_product", nullable = false)
    private Integer idProduct;

    @OneToMany(mappedBy = "companiaCel")
    private Set<DenominacionRecargaCelEntity> denominacionesRecargaCels = new LinkedHashSet<>();

}
