package mx.com.tecnetia.marcoproyectoseguridad.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.util.Constantes;
import mx.com.tecnetia.marcoproyectoseguridad.util.EncodeBase64Util;
import mx.com.tecnetia.marcoproyectoseguridad.util.FormatosUtil;
import mx.com.tecnetia.orthogonal.dto.MailDTO;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqUsuarioRepository;
import mx.com.tecnetia.orthogonal.utils.EstatusProcesoEnum;
import mx.com.tecnetia.orthogonal.utils.EstatusVerificacionEnum;
import mx.com.tecnetia.orthogonal.utils.crypto.AES;
import mx.com.tecnetia.orthogonal.utils.email.EmailOperationsThymeleafService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Log4j2
public class VerificacionEmailServiceImpl implements VerificacionEmailService {

    private static Map<String, String> otpsOfEmail = new HashMap<String, String>();
    private final ArqUsuarioRepository arqUsuarioRepository;
    private final EmailOperationsThymeleafService emailOperationsThymeleafService;
    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final String secretKey = "buef?=Bdf67ret532X";
    @Value("${server.servlet.context-path-images}")
    private String contextImages;

    @Override
    public MensajeDTO<?> enviarLinkVerificacion(String email) {
        email = email.replace(" ", "").toLowerCase();
        log.info("enviarLinkVerificacion: " + email);

        if (!FormatosUtil.direccionEmailValida(email)) {
            throw new IllegalArgumentException("La dirección de correo no tiene un formato valido.");
        }

        String api = "/verificacion/email/link/valida";
        String baseUrlWithContext = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String baseUrl = baseUrlWithContext.replace(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath(), "");
        log.info("baseUrlWithContext: {}", baseUrlWithContext);

        Optional<ArqUsuarioEntity> usuario = this.arqUsuarioRepository.findByEmail(email);
        String nombre = usuario.isPresent() ? usuario.get().getNombres() : "";

        String otp = this.getOTP(email);
        String token = AES.encrypt(email + "-" + otp, secretKey);
        token = EncodeBase64Util.encodeString(token);

        String linkDeVerificacion = baseUrlWithContext + api + "?t=" + token;
        String logoBioBox = baseUrl + contextImages + Constantes.LOGO_BIOBOX_EMAIL;

        var model = new HashMap<String, Object>();
        model.put("logoBioBox", logoBioBox);
        model.put("nombre", nombre);
        model.put("email", email);
        model.put("linkDeVerificacion", linkDeVerificacion);

        var mailDTO = new MailDTO();
        mailDTO.setMailTo(email);
        mailDTO.setMailSubject("BioBox - Verificación de correo");
        mailDTO.setModel(model);

        try {
            this.emailOperationsThymeleafService.sendEmail(mailDTO, Constantes.PLANTILLA_EMAIL_CONFIRMACION_POR_LINK);
            log.info("Email enviado correctamente");
            return new MensajeDTO<>(EstatusProcesoEnum.EXITOSO.getValue(), "Verificación de correo enviada", token);
        } catch (Exception ex) {
            log.error("Ocurrió un error al enviar el email de veirificacion: {}", ex.getMessage());
            ex.printStackTrace();
            return new MensajeDTO<>(EstatusProcesoEnum.EXITOSO.getValue(), "Error al enviar verificación de correo");
        }
    }

    @Override
    public String validarLinkVerificacion(String token) {
        log.info("validarLinkVerificacion: " + token);

        String plantilla = Constantes.PLANTILLA_HTML_LINK_CONFIRMACION_INVALIDO;
        boolean valido = false;

        try {
            String tokenDecrypt = EncodeBase64Util.decodeString(token);
            tokenDecrypt = AES.decrypt(tokenDecrypt, secretKey);

            if (tokenDecrypt != null) {
                String email = tokenDecrypt.split("-")[0];
                String otpRecibido = tokenDecrypt.split("-")[1];
                String otpEncontrado = otpsOfEmail.get(email);
                valido = otpRecibido.equals(otpEncontrado) ? true : false;
                log.info("Código válido: " + valido);

                Optional<ArqUsuarioEntity> usuario = this.arqUsuarioRepository.findByEmail(email);
                if (valido) {
                    plantilla = Constantes.PLANTILLA_HTML_LINK_CONFIRMACION_VALIDO;
                    otpsOfEmail.remove(email);
                    if (!usuario.isEmpty()) {
                        var usr = usuario.get();
                        usr.setEmailValidado(true);
                        usr.setNuevoIngreso(false);
                        this.arqUsuarioRepository.save(usr);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error al validar link: " + e.getCause());
            e.printStackTrace();
        }

        return this.processHTML(plantilla);
    }

    @Override
    public MensajeDTO<?> enviarOTP(String email) {
        email = email.replace(" ", "").toLowerCase();
        log.info("enviarOTP: " + email);

        if (!FormatosUtil.direccionEmailValida(email)) {
            throw new IllegalArgumentException("La dirección de correo no tiene un formato valido");
        }

        String otp = this.getOTP(email);
        Optional<ArqUsuarioEntity> usuario = this.arqUsuarioRepository.findByEmail(email);
        String nombre = usuario.isPresent() ? usuario.get().getNombres() : "";

        String baseUrlWithContext = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String baseUrl = baseUrlWithContext.replace(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath(), "");
        log.info("baseUrlWithContext: {}", baseUrlWithContext);

        String logoBioBox = baseUrl + contextImages + Constantes.LOGO_BIOBOX_EMAIL;

        var model = new HashMap<String, Object>();
        model.put("logoBioBox", logoBioBox);
        model.put("nombre", nombre);
        model.put("email", email);
        model.put("codigoOTP", otp);

        var mailDTO = new MailDTO();
        mailDTO.setMailTo(email);
        mailDTO.setMailSubject("BioBox - Verificación de correo");
        mailDTO.setModel(model);

        try {
            this.emailOperationsThymeleafService.sendEmail(mailDTO, Constantes.PLANTILLA_EMAIL_CONFIRMACION_POR_CODIGO);
            log.info("Email enviado correctamente");
            return new MensajeDTO<>(EstatusProcesoEnum.EXITOSO.getValue(), "Código enviado", otp);
        } catch (Exception ex) {
            log.error("Ocurrió un error al enviar el email de verificación: {}", ex.getMessage());
            ex.printStackTrace();
            throw new IllegalArgumentException("Error al enviar código");
        }

    }

    @Override
    public MensajeDTO<?> validarOTP(String email, String codigo) {
        email = email.replace(" ", "").toLowerCase();
        log.info("validarOTP: {} - {}", email, codigo);

        if (!FormatosUtil.direccionEmailValida(email)) {
            throw new IllegalArgumentException("La dirección de correo no tiene un formato valido");
        }

        Optional<ArqUsuarioEntity> usuario = this.arqUsuarioRepository.findByEmail(email);
        boolean valido = codigo.equals(otpsOfEmail.get(email)) ? true : false;
        log.info("Codigo valido: " + valido);

        if (valido) {
            if (usuario.isPresent()) {
                usuario.get().setEmailValidado(true);
                this.arqUsuarioRepository.save(usuario.get());
            }
            otpsOfEmail.remove(email);
            log.info("El correo fue verificado correctamente");
            return new MensajeDTO<>(EstatusProcesoEnum.EXITOSO.getValue(), "Tu correo fue verificado");
        } else {
            throw new IllegalArgumentException("El código es inválido");
        }
    }

    @Override
    @Transactional
    public MensajeDTO<?> estatusVerificacion(String email) {
        email = email.replace(" ", "").toLowerCase();
        log.info("estatusDeVerificacion: {}", email);

        if (!FormatosUtil.direccionEmailValida(email)) {
            throw new IllegalArgumentException("La dirección de correo no tiene un formato valido");
        }

        Optional<ArqUsuarioEntity> usuario = this.arqUsuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            if (usuario.get().getEmailValidado()) {
                log.info("El correo electrónico ya se encuentra verificado");
                return new MensajeDTO<>(EstatusVerificacionEnum.VERIFICADO.getValue(), "Tu correo electrónico ya fue verificado.");
            } else {
                log.info("El correo electrónico no esta verificado");
                return new MensajeDTO<>(EstatusVerificacionEnum.NO_VERIFICADO.getValue(), "Verifica tu correo electrónico.");
            }
        } else {
            log.info("El email no fue encontrado");
            throw new IllegalArgumentException("No se encontró el correo electrónico.");
        }
    }

    private String getOTP(String email) {
        email = email.replace(" ", "").toLowerCase();
        String otp = otpsOfEmail.get(email);

        if (otp == null) {
            otp = String.format("%02d", new Random().nextInt(900000) + 100000);
            otpsOfEmail.put(email, otp);
        }
        //otpsOfEmail.forEach((key, value) -> System.out.println(key + " " + value));
        return otp;
    }

    private String processHTML(String templateThymeleafFile) {
        String baseUrlWithContext = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String baseUrl = baseUrlWithContext.replace(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath(), "");
        log.info("baseUrlWithContext: {}", baseUrlWithContext);
        String logoBioBox = baseUrl + contextImages + Constantes.LOGO_BIOBOX_EMAIL;

        var model = new HashMap<String, Object>();
        model.put("logoBioBox", logoBioBox);

        var thymeleafContext = new Context();
        thymeleafContext.setVariables(model);

        return thymeleafTemplateEngine.process(templateThymeleafFile, thymeleafContext);
    }

}
