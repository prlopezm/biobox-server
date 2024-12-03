package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fichero_transaccion", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class FicheroTransaccionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_fichero_transaccion")
    private Long idFicheroTransaccion;
    @Basic
    @Column(name = "file")
    private byte[] file;
    @Basic
    @Column(name = "nombre_file")
    private String nombreFile;
    @Basic
    @Column(name = "signature")
    private String signature;
    @Basic
    @Column(name = "cantidad_registros")
    private Integer cantidadRegistros;
    @Basic
    @Column(name = "key_name")
    private String keyName;

}
