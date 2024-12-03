package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "servicio_usado", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ServicioUsadoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_servicio_usado")
    private Long idServicioUsado;
    @Basic
    @Column(name = "id_arq_usuario")
    private Long idArqUsuario;
    @Basic
    @Column(name = "momento")
    private Timestamp momento;
    @Basic
    @Column(name = "id_programa_prontipago")
    private Long idProgramaProntipago;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicioUsadoByIdServicioUsado")
    private Set<UsuarioPuntosColorConsumidosEntity> usuarioPuntosColorConsumidos;

}
