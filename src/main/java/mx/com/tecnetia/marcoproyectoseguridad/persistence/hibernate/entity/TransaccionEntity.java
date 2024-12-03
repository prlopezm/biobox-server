package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaccion", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class TransaccionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transaccion")
    private Long idTransaccion;
    @Basic
    @Column(name = "version")
    private char version;
    @Basic
    @Column(name = "date_time")
    private String dateTime;
    @Basic
    @Column(name = "station")
    private String station;
    @Basic
    @Column(name = "location")
    private String location;
    @Basic
    @Column(name = "bar_code")
    private String barCode;
    @Basic
    @Column(name = "line")
    private String line;
    @Basic
    @Column(name = "station_name")
    private String stationName;
    @Basic
    @Column(name = "auth_type")
    private char authType;
    @Basic
    @Column(name = "id_fichero_transaccion")
    private Long idFicheroTransaccion;

}
