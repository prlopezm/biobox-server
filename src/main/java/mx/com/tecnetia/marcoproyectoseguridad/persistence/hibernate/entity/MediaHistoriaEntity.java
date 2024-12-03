package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "media_historia", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class MediaHistoriaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_media_historia")
    private Long idMediaHistoria;
    @Basic
    @Column(name = "id_historia")
    private Long idHistoria;
    @Basic
    @Column(name = "proxima")
    private Long proxima;
    @Basic
    @Column(name = "file")
    private byte[] file;
    @Basic
    @Column(name = "nombre_file")
    private String nombreFile;
    @Basic
    @Column(name = "ext_file")
    private String extFile;

}
