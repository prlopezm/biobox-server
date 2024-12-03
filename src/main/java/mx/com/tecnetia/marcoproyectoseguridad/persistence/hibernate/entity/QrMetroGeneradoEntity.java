package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "qr_metro_generado", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class QrMetroGeneradoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_qr_metro_generado")
    private Long idQrMetroGenerado;
    @Basic
    @Column(name = "bar_code")
    private String barCode;
    @Basic
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Basic
    @Column(name = "fecha_sincronizacion")
    private LocalDateTime fechaSincronizacion;
    @Basic
    @Column(name = "id_arq_usuario")
    private Long idArqUsuario;
    @Basic
    @Column(name = "id_estacion_metro")
    private Long idEstacionMetro;

}
