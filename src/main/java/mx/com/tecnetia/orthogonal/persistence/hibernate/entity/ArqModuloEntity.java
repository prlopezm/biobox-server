package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "arq_modulo")
public class ArqModuloEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_modulo", nullable = false)
    private Long idArqModulo;
    @Basic
    @Column(name = "codigo", nullable = false, length = 100)
    private String codigo;
    @Basic
    @Column(name = "nombre", nullable = false, length = 500)
    private String nombre;
    @Basic
    @Column(name = "uri", nullable = false, length = 500)
    private String uri;
    @Basic
    @Column(name = "id_arq_aplicacion", nullable = false, insertable = false, updatable = false)
    private Long idArqAplicacion;
    @Basic
    @Column(name = "ip", nullable = false, length = 100)
    private String ip;
    @Basic
    @Column(name = "host_name", nullable = true, length = 100)
    private String hostName;
    @Basic
    @Column(name = "port", nullable = false)
    private Integer port;
    @Basic
    @Column(name = "http", nullable = false, length = 100)
    private String http;
    @ManyToOne
    @JoinColumn(name = "id_arq_aplicacion", referencedColumnName = "id_arq_aplicacion", nullable = false)
    private ArqAplicacionEntity arqAplicacionByIdArqAplicacion;

    public Long getIdArqModulo() {
        return idArqModulo;
    }

    public void setIdArqModulo(Long idArqModulo) {
        this.idArqModulo = idArqModulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Long getIdArqAplicacion() {
        return idArqAplicacion;
    }

    public void setIdArqAplicacion(Long idArqAplicacion) {
        this.idArqAplicacion = idArqAplicacion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArqModuloEntity that = (ArqModuloEntity) o;
        return Objects.equals(idArqModulo, that.idArqModulo) && Objects.equals(codigo, that.codigo) && Objects.equals(nombre, that.nombre) && Objects.equals(uri, that.uri) && Objects.equals(idArqAplicacion, that.idArqAplicacion) && Objects.equals(ip, that.ip) && Objects.equals(hostName, that.hostName) && Objects.equals(port, that.port) && Objects.equals(http, that.http);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqModulo, codigo, nombre, uri, idArqAplicacion, ip, hostName, port, http);
    }

    public ArqAplicacionEntity getArqAplicacionByIdArqAplicacion() {
        return arqAplicacionByIdArqAplicacion;
    }

    public void setArqAplicacionByIdArqAplicacion(ArqAplicacionEntity arqAplicacionByIdArqAplicacion) {
        this.arqAplicacionByIdArqAplicacion = arqAplicacionByIdArqAplicacion;
    }
}
