package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario_puntos_color_consumidos", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class UsuarioPuntosColorConsumidosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_usuario_puntos_color_consumidos")
    private Long idUsuarioPuntosColorAcumulado;
    @Basic
    @Column(name = "puntos")
    private Integer puntos;
    @Basic
    @Column(name = "id_color", insertable = false, updatable = false)
    private Integer idColor;

    @Basic
    @Column(name = "id_servicio_usado", nullable = false, insertable = false, updatable = false)
    private Long idServicioUsado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color", referencedColumnName = "id_color", nullable = false)
    private ColorEntity colorByIdColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio_usado", referencedColumnName = "id_servicio_usado", nullable = false)
    private ServicioUsadoEntity servicioUsadoByIdServicioUsado;

}
