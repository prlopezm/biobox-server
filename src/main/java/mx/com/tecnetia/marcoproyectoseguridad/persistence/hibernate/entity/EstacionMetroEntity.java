package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estacion_metro", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class EstacionMetroEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_estacion_metro")
    private Long idEstacionMetro;
    @Basic
    @Column(name = "codigo")
    private String codigo;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "direccion")
    private String direccion;
    @Basic
    @Column(name = "gmap")
    private String gmap;
    @Basic
    @Column(name = "latitud")
    private String latitud;
    @Basic
    @Column(name = "longitud")
    private String longitud;
    @Basic
    @Column(name = "id_linea")
    private Integer idLinea;
    @Basic
    @Column(name = "nombre_foto")
    private String nombreFoto;
    @Basic
    @Column(name = "foto")
    private byte[] foto;
    @Basic
    @Column(name = "url_foto")
    private String urlFoto;
}
