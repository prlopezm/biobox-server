package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

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
@Table(name = "color", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class ColorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_color")
    private Integer idColor;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "hexadecimal")
    private String hexadecimal;
    @Basic
    @Column(name = "url_foto")
    private String urlFoto;
    @Basic
    @Column(name = "url_foto_2")
    private String urlFoto2;

}
