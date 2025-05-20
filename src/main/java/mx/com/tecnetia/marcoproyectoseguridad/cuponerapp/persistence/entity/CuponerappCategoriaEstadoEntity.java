package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuponera_categoria_estado")
public class CuponerappCategoriaEstadoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cuponera_categoria_estado")
    private Long idCuponerappCategoriaEstado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuponera", referencedColumnName = "id_promocion", nullable = false)
    private CuponerappEntity cuponerapp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = false)
    private CategoriaEntity categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado", nullable = false)
    private EstadoEntity estado;
}
