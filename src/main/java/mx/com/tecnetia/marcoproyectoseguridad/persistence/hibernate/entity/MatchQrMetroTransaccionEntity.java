package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "match_qr_metro_transaccion", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class MatchQrMetroTransaccionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "match_qr_metro_transaccion")
    private Long matchQrMetroTransaccion;
    @Basic
    @Column(name = "id_transaccion")
    private Long idTransaccion;
    @Basic
    @Column(name = "id_qr_metro_generado")
    private Long idQrMetroGenerado;

}
