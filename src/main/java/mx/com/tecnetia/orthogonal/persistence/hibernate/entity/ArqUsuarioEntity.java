package mx.com.tecnetia.orthogonal.persistence.hibernate.entity;

import jakarta.persistence.*;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;
import java.sql.Date;
import java.util.Objects;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "arq_usuario")
    public class ArqUsuarioEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_arq_usuario", nullable = false)
    private Long idArqUsuario;
    @Basic
    @Column(name = "nombres", nullable = true, length = 200)
    private String nombres;
    @Basic
    @Column(name = "passw", nullable = false, length = 1000)
    private String passw;
    @Basic
    @Column(name = "apellido_paterno", nullable = true, length = 100)
    private String apellidoPaterno;
    @Basic
    @Column(name = "apellido_materno", nullable = true, length = 100)
    private String apellidoMaterno;
    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Basic
    @Column(name = "activo", nullable = false)
    private Boolean activo;
    @Basic
    @Column(name = "nick", nullable = false, length = 200)
    private String nick;
    @Basic
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;
    @Basic
    @Column(name = "email_validado", nullable = false)
    private Boolean emailValidado;
    @Basic
    @Column(name = "telefono_validado", nullable = false)
    private Boolean telefonoValidado;
    @Basic
    @Column(name = "registro_concluido", nullable = false)
    private Boolean registroConcluido;
    @Basic
    @Column(name = "pendiente_confirmacion", nullable = false)
    private Boolean pendienteConfirmacion;


    @Basic
    @Column(name = "nuevo_ingreso", nullable = false)
    private Boolean nuevoIngreso;
    @Basic
    @Column(name = "id_arq_cliente", nullable = false, insertable = false, updatable = false)
    private Long idArqCliente;
    @Basic
    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;
    @ManyToOne
    @JoinColumn(name = "id_arq_cliente", referencedColumnName = "id_arq_cliente", nullable = false)
    private ArqClienteEntity arqClienteByIdArqCliente;
    @OneToMany(mappedBy = "arqUsuarioByIdArqUsuario")
    private Collection<ArqUsuarioRolEntity> arqUsuarioRolesByIdArqUsuario;

    public Collection<ArqUsuarioRolEntity> getArqUsuarioRolesByIdArqUsuario() {
        return arqUsuarioRolesByIdArqUsuario;
    }

    public void setArqUsuarioRolesByIdArqUsuario(Collection<ArqUsuarioRolEntity> arqUsuarioRolesByIdArqUsuario) {
        this.arqUsuarioRolesByIdArqUsuario = arqUsuarioRolesByIdArqUsuario;
    }

    public Long getIdArqUsuario() {
        return idArqUsuario;
    }

    public void setIdArqUsuario(Long idArqUsuario) {
        this.idArqUsuario = idArqUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getPendienteConfirmacion() {
        return pendienteConfirmacion;
    }

    public void setPendienteConfirmacion(Boolean pendienteConfirmacion) {
        this.pendienteConfirmacion = pendienteConfirmacion;
    }

    public Long getIdArqCliente() {
        return idArqCliente;
    }

    public void setIdArqCliente(Long idArqCliente) {
        this.idArqCliente = idArqCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArqUsuarioEntity that = (ArqUsuarioEntity) o;
        return Objects.equals(idArqUsuario, that.idArqUsuario) && Objects.equals(nombres, that.nombres) && Objects.equals(passw, that.passw) && Objects.equals(apellidoPaterno, that.apellidoPaterno) && Objects.equals(apellidoMaterno, that.apellidoMaterno) && Objects.equals(email, that.email) && Objects.equals(activo, that.activo) && Objects.equals(nick, that.nick) && Objects.equals(telefono, that.telefono) && Objects.equals(pendienteConfirmacion, that.pendienteConfirmacion) && Objects.equals(idArqCliente, that.idArqCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArqUsuario, nombres, passw, apellidoPaterno, apellidoMaterno, email, activo, nick, telefono, pendienteConfirmacion, idArqCliente);
    }

    public ArqClienteEntity getArqClienteByIdArqCliente() {
        return arqClienteByIdArqCliente;
    }

    public void setArqClienteByIdArqCliente(ArqClienteEntity arqClienteByIdArqCliente) {
        this.arqClienteByIdArqCliente = arqClienteByIdArqCliente;
    }

    @OneToOne(mappedBy = "arqUsuarioEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private FotoUsuarioEntity fotoUsuarioEntity;

    @OneToOne(mappedBy = "arqUsuarioEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UsuarioPuntosEntity usuarioPuntosEntity;

    public FotoUsuarioEntity getFotoUsuarioEntity() {
        return fotoUsuarioEntity;
    }

    public void setIdArqCliente(FotoUsuarioEntity fotoUsuarioEntity) {
        this.fotoUsuarioEntity = fotoUsuarioEntity;
    }

    public UsuarioPuntosEntity getUsuarioPuntosEntity() {
        return usuarioPuntosEntity;
    }

    public void setUsuarioPuntosEntity(UsuarioPuntosEntity usuarioPuntosEntity) {
        this.usuarioPuntosEntity = usuarioPuntosEntity;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

	public Boolean getEmailValidado() {
		return emailValidado;
	}

	public void setEmailValidado(Boolean emailValidado) {
		this.emailValidado = emailValidado;
	}

	public Boolean getTelefonoValidado() {
		return telefonoValidado;
	}

	public void setTelefonoValidado(Boolean telefonoValidado) {
		this.telefonoValidado = telefonoValidado;
	}

	public Boolean getRegistroConcluido() {
		return registroConcluido;
	}

	public void setRegistroConcluido(Boolean registroConcluido) {
		this.registroConcluido = registroConcluido;
	}

    public Boolean getNuevoIngreso() {
        return nuevoIngreso;
    }

    public void setNuevoIngreso(Boolean nuevoIngreso) {
        this.nuevoIngreso = nuevoIngreso;
    }
}
