package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "anuncio", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class AnuncioEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_anuncio")
    private Long idAnuncio;
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
    @Column(name = "url_foto")
    private String urlFoto;
}
