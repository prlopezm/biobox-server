package mx.com.tecnetia.orthogonal.services;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ColorEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.ColorEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.FotoUsuarioEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.service.VerificacionEmailService;
import mx.com.tecnetia.marcoproyectoseguridad.service.VerificacionTelefonoService;
import mx.com.tecnetia.orthogonal.dto.EditaUsuarioArquitecturaDTO;
import mx.com.tecnetia.orthogonal.dto.MailDTO;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioRolEntity;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.FotoUsuarioEntity;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqClienteEntityRepository;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqRolAplicacionRepository;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqUsuarioRepository;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqUsuarioRolAplicacionEntityRepository;
import mx.com.tecnetia.orthogonal.security.EstatusUsuarioDTO;
import mx.com.tecnetia.orthogonal.security.NuevoUsuarioArquitecturaDTO;
import mx.com.tecnetia.orthogonal.security.UsuarioPrincipal;
import mx.com.tecnetia.orthogonal.security.auth.AuthenticationFacadeComponent;
import mx.com.tecnetia.orthogonal.utils.EstatusProcesoEnum;
import mx.com.tecnetia.orthogonal.utils.EstatusVerificacionEnum;
import mx.com.tecnetia.orthogonal.utils.crypto.AES;
import mx.com.tecnetia.orthogonal.utils.email.EmailOperationsThymeleafService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Log4j2
public class UsuarioServiceImpl implements UsuarioService {

    private final ArqUsuarioRepository arqUsuarioRepository;
    private final ArqRolAplicacionRepository arqRolAplicacionRepository;
    private final PasswordEncoder passwordEncoder;
    private final ArqUsuarioRolAplicacionEntityRepository arqUsuarioRolAplicacionEntityRepository;
    private final ArqClienteEntityRepository arqClienteEntityRepository;
    private final AuthenticationFacadeComponent authenticationFacadeComponent;
    private final UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;
    private final ColorEntityRepository colorEntityRepository;
    private final FotoUsuarioEntityRepository fotoUsuarioEntityRepository;
    private final EmailOperationsThymeleafService emailOperationsThymeleafService;
    private final VerificacionEmailService verificacionEmailService;
    private final VerificacionTelefonoService verificacionTelefonoService;
    @Resource
    @Lazy
    private UsuarioServiceImpl usuarioServiceImpl;

    @Value("${cliente.codigo}")
    private String codigoCliente;

    final String secretKey = "ssshhhhhhhhhhh!!!!";

    @Override
    @Transactional(readOnly = true)
    public ArqUsuarioEntity findById(Long id) {
        return this.arqUsuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario especificado."));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArqUsuarioEntity> getByNick(String nick) {
        return this.arqUsuarioRepository.findByNick(nick);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArqUsuarioEntity> getByEmail(String email) {
        return this.arqUsuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArqUsuarioEntity> getByTelefono(String telefono) {
        return this.arqUsuarioRepository.findByTelefono(telefono);
    }

    @Override
    @Transactional(readOnly = true)
    public ArqUsuarioEntity getUsuarioLogeado() {
        var usuarioLogeado = (UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal();
        return this.arqUsuarioRepository.findByEmail(usuarioLogeado.getEmail().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario especificado."));
    }

    @Override
    @Transactional
    public MensajeDTO<?> crearUsuario(NuevoUsuarioArquitecturaDTO nuevoUsuario, byte[] foto, String nombreFoto) {

        var usuarioEntity = new ArqUsuarioEntity();
        usuarioEntity.setActivo(true);
        usuarioEntity.setNombres(nuevoUsuario.getNombres());
        usuarioEntity.setNick(nuevoUsuario.getEmail().toLowerCase());
        usuarioEntity.setTelefono(nuevoUsuario.getTelefono());
        usuarioEntity.setPassw(passwordEncoder.encode(nuevoUsuario.getPassw()));
        usuarioEntity.setApellidoMaterno(nuevoUsuario.getApellidoMaterno());
        usuarioEntity.setApellidoPaterno(nuevoUsuario.getApellidoPaterno());
        usuarioEntity.setEmail(nuevoUsuario.getEmail().toLowerCase());
        usuarioEntity.setPendienteConfirmacion(false);
        usuarioEntity.setFechaRegistro(Date.valueOf(LocalDate.now()));
        usuarioEntity.setEmailValidado(false);
        usuarioEntity.setTelefonoValidado(false);
        usuarioEntity.setRegistroConcluido(false);
        usuarioEntity.setNuevoIngreso(true);

        //Valida existencia de usuario
        MensajeDTO<?> mensajeDTO = this.existenIdentificadoresAlCrear(nuevoUsuario.getEmail(), nuevoUsuario.getNick(), nuevoUsuario.getTelefono());
        if (Boolean.valueOf(mensajeDTO.getEstatus())) {
            throw new IllegalArgumentException(mensajeDTO.getMensaje());
        }

        //Asigna cliente
        var cliente = this.arqClienteEntityRepository.findByCodigoAndActivoIsTrue(codigoCliente);
        if (cliente.isPresent()) {
            usuarioEntity.setArqClienteByIdArqCliente(cliente.get());
        } else {
            throw new IllegalArgumentException("No está asignado el código del cliente");
        }

        //Asigna rol
        var rolEsp = this.arqRolAplicacionRepository.findByNombre(nuevoUsuario.getRol());
        if (rolEsp.isPresent()) {
            usuarioEntity = this.arqUsuarioRepository.save(usuarioEntity);
            var arqUsuarioRolAplicacion = new ArqUsuarioRolEntity();
            arqUsuarioRolAplicacion.setArqUsuarioByIdArqUsuario(usuarioEntity);
            arqUsuarioRolAplicacion.setArqRolByIdArqRol(rolEsp.get());
            this.arqUsuarioRolAplicacionEntityRepository.save(arqUsuarioRolAplicacion);
        } else {
            throw new IllegalArgumentException("El rol especificado no es válido.");
        }

        //Asigna puntos
        var usuarioPuntosColorEntity = new UsuarioPuntosColorEntity();
        for (ColorEntity colorEntity : this.colorEntityRepository.findAll()) {
            usuarioPuntosColorEntity = new UsuarioPuntosColorEntity();
            usuarioPuntosColorEntity.setArqUsuarioByIdArqUsuario(usuarioEntity);
            usuarioPuntosColorEntity.setPuntos(0);
            usuarioPuntosColorEntity.setColorByIdColor(colorEntity);
            this.usuarioPuntosColorEntityRepository.save(usuarioPuntosColorEntity);
        }

        //Guarda foto
        var fotoUsuarioEntity = new FotoUsuarioEntity();
        fotoUsuarioEntity.setArqUsuarioEntity(usuarioEntity);
        fotoUsuarioEntity.setNombreFoto(nombreFoto);
        fotoUsuarioEntity.setFoto(foto);
        this.fotoUsuarioEntityRepository.save(fotoUsuarioEntity);

        //Envia link de verificacion de correo
        verificacionEmailService.enviarLinkVerificacion(nuevoUsuario.getEmail().toLowerCase());
        log.info("Usuario de arquitectura guardado satisfactorimente.");

        return new MensajeDTO<Long>(String.valueOf(true), "Por favor verifica tu correo electrónico, se envio un link de verificación a tu correo.", usuarioEntity.getIdArqUsuario());
    }

    @Override
    @Transactional
    public MensajeDTO<?> editarUsuario(EditaUsuarioArquitecturaDTO datos) {
        var usuarioLogeado = this.usuarioServiceImpl.getUsuarioLogeado();
        //Valida existencia de identificadores:
        this.existenIdentificadoresAlEditar(usuarioLogeado, datos.getNick(), datos.getEmail(), datos.getTelefono());

        log.info("Editar usuario: {}", datos);
        usuarioLogeado.setNombres(datos.getNombres());
        usuarioLogeado.setApellidoPaterno(datos.getApellidoPaterno());
        usuarioLogeado.setApellidoMaterno(datos.getApellidoMaterno());
        usuarioLogeado.setNick(datos.getEmail().toLowerCase().strip());
        usuarioLogeado.setEmail(datos.getEmail().toLowerCase().strip());
        usuarioLogeado.setTelefono(datos.getTelefono().strip());
        usuarioLogeado.setEmailValidado(datos.isEmailValidado());
        usuarioLogeado.setTelefonoValidado(datos.isTelefonoValidado());
        usuarioLogeado.setRegistroConcluido(this.verificaRegistro(usuarioLogeado));
        try {
            this.arqUsuarioRepository.save(usuarioLogeado);
        } catch (Exception e) {
            log.error("Error. Probablemente haya otro usuario con los mismos datos.");
            return new MensajeDTO<>(EstatusProcesoEnum.FALLIDO.getValue(), "Probablemente haya otro usuario con los mismos datos");
        }


        return new MensajeDTO<>(EstatusProcesoEnum.EXITOSO.getValue(), "Tu información se actualizo correctamente");
    }

    @Override
    @Transactional
    public MensajeDTO<?> editarContrasenia(String correo, String passw) {
        Optional<ArqUsuarioEntity> arqUsuarioEnt = arqUsuarioRepository.findByEmail(correo);

        if (arqUsuarioEnt.isPresent()) {
            arqUsuarioEnt.get().setPassw(passwordEncoder.encode(passw));
            this.arqUsuarioRepository.save(arqUsuarioEnt.get());
            return new MensajeDTO<>(EstatusProcesoEnum.EXITOSO.getValue(), "Tu contraseña se modificó correctamente");
        } else {
            throw new IllegalArgumentException("No existe el usuario especificado.");
        }
    }

    @Override
    @Transactional
    public void recuperarContrasenia(String userMail) {
        ArqUsuarioEntity arqUsuarioEnt = arqUsuarioRepository.findByEmail(userMail)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario especificado."));
        String pwd = AES.obtenerPalabraAleatoria();
        arqUsuarioEnt.setPassw(passwordEncoder.encode(pwd));
        this.arqUsuarioRepository.save(arqUsuarioEnt);

        var model = new HashMap<String, Object>();
        model.put("contrasenia", pwd);
        var templateThymeleafFile = "cambio-pwd-email-thymeleaf.html";
        var mailDTO = new MailDTO();
        mailDTO.setMailTo(userMail);
        mailDTO.setMailSubject("BioBox - Nueva contraseña");
        mailDTO.setModel(model);
        try {
            this.emailOperationsThymeleafService.sendEmail(mailDTO, templateThymeleafFile);
        } catch (Exception ex) {
            log.error("Ocurrió un error al enviar el email con la nueva contraseña: {}", ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void editarContraseniaPendienteConfirmacion(String correo, String contrasenia) {
        ArqUsuarioEntity arqUsuarioEnt = arqUsuarioRepository.findByEmail(correo)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario especificado."));

        if (arqUsuarioEnt.getPendienteConfirmacion().booleanValue()) {
            arqUsuarioEnt.setPassw(passwordEncoder.encode(contrasenia));
            arqUsuarioEnt.setPendienteConfirmacion(false);
            this.arqUsuarioRepository.save(arqUsuarioEnt);
        } else {
            throw new IllegalArgumentException("No se puede cambiar la contraseña del usuario.");
        }
    }

    @Override
    @Transactional
    public void desactivarUsuario(String correo) {
        ArqUsuarioEntity arqUsuarioEnt = arqUsuarioRepository.findByEmail(correo)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario especificado."));
        var usuarioLogeado = this.usuarioServiceImpl.getUsuarioLogeado();
        if(!Objects.equals(usuarioLogeado.getIdArqUsuario(), arqUsuarioEnt.getIdArqUsuario())) {
            throw new IllegalArgumentException("No puedes eliminar otro usuario distinto al tuyo.");
        }
        this.arqUsuarioRepository.delete(arqUsuarioEnt);
        /*arqUsuarioEnt.setActivo(false);
        this.arqUsuarioRepository.save(arqUsuarioEnt);*/
    }

    @Override
    @Transactional(readOnly = true)
    public EstatusUsuarioDTO existeUsuario(String nick) {
        EstatusUsuarioDTO estatusUsuarioDTO = new EstatusUsuarioDTO();
        var arqUsuarioEntityOpt = this.arqUsuarioRepository.findByNick(nick.toLowerCase().trim());
        if (arqUsuarioEntityOpt.isEmpty()) {
            estatusUsuarioDTO.setExisteUsuario(false);

            return estatusUsuarioDTO;
        }

        var arqUsuarioEntity = arqUsuarioEntityOpt.get();
        if (Boolean.TRUE.equals(arqUsuarioEntity.getActivo())) {
            estatusUsuarioDTO.setExisteUsuario(true);
            estatusUsuarioDTO.setPendienteConfirmacion(arqUsuarioEntity.getPendienteConfirmacion());
            return estatusUsuarioDTO;
        }
        throw new IllegalArgumentException("Tu cuenta de usuario se encuentra deshabilitada. Contacta a un administrador.");
    }


    private MensajeDTO<?> existenIdentificadoresAlCrear(String nick, String email, String telefono) {
        if (this.arqUsuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Esta dirección de correo electrónico ya está en uso. Por favor, pruebe con otra");
        } else if (this.arqUsuarioRepository.findByNick(nick).isPresent()) {
            throw new IllegalArgumentException("Este email ya está en uso. Por favor, pruebe con otro");
        } else if (!this.arqUsuarioRepository.findByTelefonoAllIgnoreCase(telefono).isEmpty()) {
            throw new IllegalArgumentException("Este número de teléfono ya está en uso. Por favor, pruebe con otro");
        } else {
            return new MensajeDTO<>(String.valueOf(false), null);
        }
    }

    private void existenIdentificadoresAlEditar(ArqUsuarioEntity usuarioLogeado, String nick, String email, String telefono) {
        var usuarioConIgualEmail = this.arqUsuarioRepository.findByEmail(email);
        log.info("Usuario con igual email: {}", usuarioConIgualEmail);
        var usuarioConIgualNick = this.arqUsuarioRepository.findByNick(nick);
        log.info("Usuario con igual nick: {}", usuarioConIgualNick);
        var usuariosConIgualTelefono = this.arqUsuarioRepository.findByTelefonoAllIgnoreCase(telefono);
        log.info("Usuarios con igual telefono: {}", usuariosConIgualTelefono);
        var listaTelefonosContieneUsuarioLogeado = usuariosConIgualTelefono.stream()
                .anyMatch(v -> Objects.equals(v.getIdArqCliente(), usuarioLogeado.getIdArqCliente()));
        log.info("La lista de teléfonos iguales contiene al del usuario firmado: {}", listaTelefonosContieneUsuarioLogeado);

        //Un usuario tiene el email que intentamos poner:
        if (usuarioConIgualEmail.isPresent() &&
                //Ese usuario es distinto al usuario logeado:
                !Objects.equals(usuarioLogeado.getIdArqUsuario(), usuarioConIgualEmail.get().getIdArqUsuario())) {
            throw new IllegalArgumentException("Esta dirección de correo electrónico ya está en uso. Por favor, pruebe con otra");
            //Un usuario tiene el nick que intentamos poner:
        } else if (usuarioConIgualNick.isPresent() &&
                //Ese usuario es distinto al usuario logeado:
                !Objects.equals(usuarioLogeado.getIdArqUsuario(), usuarioConIgualNick.get().getIdArqUsuario())) {
            throw new IllegalArgumentException("Este alias de usuario ya está en uso. Por favor, pruebe con otro");
            //Existe 0 o más de 1 usuario con el teléfono que intentamos poner:
        } else if (!usuariosConIgualTelefono.isEmpty() && !listaTelefonosContieneUsuarioLogeado) {
            throw new IllegalArgumentException("Este número de teléfono ya está en uso. Por favor, pruebe con otro");
        }else if (usuariosConIgualTelefono.size() > 1) {
            throw new IllegalArgumentException("Este número de teléfono ya está en uso. Por favor, pruebe con otro");
        }
    }

    @Override
    @Transactional
    public String estatusVerificacion(ArqUsuarioEntity usuario) {
        StringBuilder mensaje = new StringBuilder();
        List<String> faltaVerificar = new ArrayList<String>();

        if (this.verificacionEmailService.estatusVerificacion(
                usuario.getEmail()).getEstatus().equals(
                EstatusVerificacionEnum.NO_VERIFICADO.getValue())) {
            faltaVerificar.add("correo electrónico");
        }
        if (this.verificacionTelefonoService.estatusVerificacion(
                usuario.getTelefono()).getEstatus().equals(
                EstatusVerificacionEnum.NO_VERIFICADO.getValue())) {
            faltaVerificar.add("número de teléfono");
        }

        //Mensaje
        if (faltaVerificar.size() > 0) {
            mensaje.append("Verifica tus datos: ");
            AtomicInteger index = new AtomicInteger();
            faltaVerificar.forEach(item -> {
                mensaje.append(item);
                if ((index.getAndIncrement() + 1) < faltaVerificar.size())
                    mensaje.append(", ");
            });
        }

        if (mensaje.isEmpty()) {
            return EstatusVerificacionEnum.VERIFICADO.getValue();
        } else {
            return mensaje.toString();
        }
    }

    @Override
    @Transactional
    public boolean registroConcluido(ArqUsuarioEntity usuario) {
        if (usuario.getRegistroConcluido()) {
            return true;
        }
        if (usuario.getEmailValidado() &&
                usuario.getTelefonoValidado()) {
            usuario.setRegistroConcluido(true);
            this.arqUsuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    private boolean verificaRegistro(ArqUsuarioEntity usuario) {
        if (usuario.getRegistroConcluido()) {
            return true;
        }
        return usuario.getEmailValidado() &&
                usuario.getTelefonoValidado();
    }

}
