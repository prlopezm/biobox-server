package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "linea", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class LineaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_linea")
    private Integer idLinea;
    @Basic
    @Column(name = "numero")
    private Integer numero;
    @Basic
    @Column(name = "color")
    private String color;
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
