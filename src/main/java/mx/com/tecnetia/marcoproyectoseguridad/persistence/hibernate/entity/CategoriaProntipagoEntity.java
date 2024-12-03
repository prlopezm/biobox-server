package mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categoria_prontipago", schema = "public", catalog = "BIOBOX")
@Getter
@Setter
public class CategoriaProntipagoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_categoria_prontipago")
    private Long idCategoriaProntipago;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "contacto")
    private String contacto;
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "color_fondo")
    private String colorFondo;
    @Basic
    @Column(name = "tipo_programa")
    private String tipoPrograma;
    @Basic
    @Column(name = "url_logo")
    private String urlLogo;
}
