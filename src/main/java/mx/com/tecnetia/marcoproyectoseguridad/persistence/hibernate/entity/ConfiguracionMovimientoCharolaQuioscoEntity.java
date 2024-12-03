package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "configuracion_movimiento_charola_quiosco", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ConfiguracionMovimientoCharolaQuioscoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_configuracion_movimiento_charola_quiosco")
    private Long idConfiguracionMovimientoCharolaQuiosco;
    @Basic
    @Column(name = "id_quiosco")
    private Long idQuiosco;
    @Basic
    @Column(name = "id_movimiento_charola", nullable = false, insertable = false, updatable = false)
    private Integer idMovimientoCharola;
    @Basic
    @Column(name = "id_material")
    private Integer idMaterial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_movimiento_charola", referencedColumnName = "id_movimiento_charola", nullable = false)
    private MovimientoCharolaEntity movimientoCharolaByIdMovimientoCharola;

}
