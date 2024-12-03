package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ayuda", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class AyudaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_ayuda")
    private Integer idAyuda;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "anterior")
    private Integer anterior;
    @Basic
    @Column(name = "file")
    private byte[] file;
    @Basic
    @Column(name = "nombre_file")
    private String nombreFile;
    @Basic
    @Column(name = "ext_file")
    private String extFile;
    @Basic
    @Column(name = "texto")
    private String texto;
    @Basic
    @Column(name = "url_foto")
    private String urlFoto;

}
