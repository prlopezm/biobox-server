package mx.com.tecnetia.orthogonal.ampq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Integer idArqUsuario;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private Boolean activo;
    private String nick;
    private String telefono;
    private Boolean emailValidado;
    private Boolean telefonoValidado;
    private Boolean registroConcluido;
    private Boolean pendienteConfirmacion;
    private Boolean nuevoIngreso;
    private Integer idArqCliente;
    private Object usuarioPuntosEntity;
}
