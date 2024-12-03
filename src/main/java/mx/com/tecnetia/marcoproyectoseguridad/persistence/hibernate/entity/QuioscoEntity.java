package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "quiosco", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class QuioscoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_quiosco")
    private Long idQuiosco;
    @Basic
    @Column(name = "direccion")
    private String direccion;
    @Basic
    @Column(name = "latitud")
    private String latitud;
    @Basic
    @Column(name = "longitud")
    private String longitud;
    @Basic
    @Column(name = "qr")
    private java.util.UUID qr;
    @Basic
    @Column(name = "ip")
    private String ip;
    @Basic
    @Column(name = "altura_llenado_mm")
    private Integer alturaLlenadoMm;
    @Basic
    @Column(name = "tipo_arduino")
    private Boolean tipoArduino;
    @Basic
    @Column(name = "nombre")
    private String nombre;
}
