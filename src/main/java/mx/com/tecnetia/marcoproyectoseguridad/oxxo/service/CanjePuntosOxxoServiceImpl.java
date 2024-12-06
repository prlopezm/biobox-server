package mx.com.tecnetia.marcoproyectoseguridad.oxxo.service;

import com.google.gson.Gson;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.canje.CanjeRequest;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.canje.CanjeResponseDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity.CanjeOxxoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity.OpcionCanjeOxxoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository.CanjeOxxoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository.OpcionCanjeOxxoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.util.Constantes;
import mx.com.tecnetia.orthogonal.dto.MailDTO;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import mx.com.tecnetia.orthogonal.utils.email.EmailOperationsThymeleafService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-04
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class CanjePuntosOxxoServiceImpl implements CanjePuntosOxxoService {
    @Value("${OXXO_LIMITE_EXCEDIDO_ERROR}")
    private String msgErrorCantidadCanjesExcedido;
    @Value("${server.servlet.context-path-images}")
    private String contextImages;
    private final UsuarioService usuarioService;
    private final OpcionCanjeOxxoEntityRepository opcionCanjeOxxoEntityRepository;
    private final OxxoCommonsService oxxoCommonsService;
    private final CanjeOxxoEntityRepository canjeOxxoEntityRepository;
    private final EmailOperationsThymeleafService emailOperationsThymeleafService;
    private final UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;

    @Resource
    @Lazy
    private CanjePuntosOxxoServiceImpl canjePuntosOxxoServiceImpl;

    @Override
    @Transactional
    public String canjearPuntos(@NotNull Integer id) {
        var usuarioFirmado = this.usuarioService.getUsuarioLogeado();
        var opcionCanje = this.opcionCanjeOxxoEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la opción de canje especificada."));
        verificaSiPuedeCanjearPuntos(usuarioFirmado.getIdArqUsuario(), opcionCanje.getPuntosCanjear());
        var ret = oxxoCommonsService.validarUsuario(usuarioFirmado.getTelefono());
        var respuesta = canjearPuntos(usuarioFirmado.getIdArqUsuario(), ret.getData().getMember_id(), opcionCanje);
        descuentaPuntosCanje(usuarioFirmado.getIdArqUsuario(), opcionCanje.getPuntosCanjear());
        var baseUrlWithContext = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        var baseUrl = baseUrlWithContext.replace(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath(), "");
        canjePuntosOxxoServiceImpl.enviarEmail(usuarioFirmado.getEmail(), respuesta.getData().getTicket_id(), respuesta.getData().getTransaction_id().toString(), baseUrl);
        return "Canje completado con éxito. Recibirás un email con la información.";
    }

    void verificaSiPuedeCanjearPuntos(@NotNull Long usuarioFirmadoId, @NotNull Integer puntos) {
        if (oxxoCommonsService.cantidadPuntosUsuarioFirmado(usuarioFirmadoId) < puntos) {
            throw new IllegalArgumentException("Debe obtener más puntos BioBox");
        }
        if (oxxoCommonsService.cantidadCanjesRestantes(usuarioFirmadoId) < puntos) {
            var msg = this.oxxoCommonsService.propiedadesOxxo()
                    .stream().filter(p -> Objects.equals(p.codigo(), msgErrorCantidadCanjesExcedido))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Error en la BD. Falta mensaje de error por excesos de canjes"))
                    .valor();
            log.error("El usuario {} excedió la cantidad de canjes mensuales.", usuarioFirmadoId);
            throw new IllegalArgumentException(msg);
        }
    }

    CanjeResponseDTO canjearPuntos(Long usuarioFirmadoId, @NotBlank String memberId, @NotNull OpcionCanjeOxxoEntity opcionCanjeOxxoEntity) {
        var paramLlamada = this.oxxoCommonsService.buildParametrosCanjePuntos();
        var urlEndpoint = paramLlamada.url();
        try {
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", paramLlamada.key());
            headers.set("partner-id", paramLlamada.partnerId());
            var body = new CanjeRequest(memberId, opcionCanjeOxxoEntity.getPuntosCanjear(), Integer.parseInt(paramLlamada.partnerId()));
            var requestEntity = new HttpEntity<>(body, headers);
            var responseData = restTemplate.postForEntity(urlEndpoint, requestEntity, CanjeResponseDTO.class);
            log.info("Recibimos de Oxxo: {}", responseData.getBody());
            guardaCanje(usuarioFirmadoId, opcionCanjeOxxoEntity, true,
                    new Gson().toJson(Objects.requireNonNull(responseData.getBody())));
            return responseData.getBody();
        } catch (RestClientException e) {
            guardaCanje(usuarioFirmadoId, opcionCanjeOxxoEntity, false, e.getMessage());
            log.error("No se pudo invocar al servicio de canje de puntos de Oxxo: {}", e.getMessage());
            throw new IllegalStateException("Fallo al invocar al servicio canje de puntos de Oxxo");
        }
    }

    void guardaCanje(@NotNull Long usuarioFirmadoId, @NotNull OpcionCanjeOxxoEntity opcionCanjeOxxoEntity, boolean exitoso, String respuestaOxxo) {
        var ent = new CanjeOxxoEntity()
                .setArqUsuarioId(usuarioFirmadoId)
                .setExitoso(exitoso)
                .setMomento(LocalDateTime.now())
                .setOpcionCanjeOxxo(opcionCanjeOxxoEntity)
                .setRespuestaOxxo(respuestaOxxo);
        canjeOxxoEntityRepository.save(ent);
    }


    @Async
    public void enviarEmail(@Email @NotBlank String email, @NotBlank String ticket, @NotBlank String transaction, String baseUrl) {
/*        String baseUrlWithContext = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String baseUrl = baseUrlWithContext.replace(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath(), "");
        log.info("baseUrlWithContext: {}", baseUrlWithContext);*/
        String logoBioBox = baseUrl + contextImages + Constantes.LOGO_BIOBOX_EMAIL;

        var model = new HashMap<String, Object>();
        model.put("logoBioBox", logoBioBox);
        model.put("ticket", ticket);
        model.put("transaction", transaction);
        model.put("email", email);

        var mailDTO = new MailDTO();
        mailDTO.setMailTo(email);
        mailDTO.setMailSubject("BioBox - Canje Spin");
        mailDTO.setModel(model);

        try {
            this.emailOperationsThymeleafService.sendEmail(mailDTO, Constantes.PLANTILLA_EMAIL_CANJE_OXXO);
            log.info("Email enviado correctamente");
        } catch (Exception ex) {
            log.error("Ocurrió un error al enviar el email de canje Oxxo: {}", ex.getMessage());
            throw new IllegalStateException("Ocurrió un error al enviar el email de canje Oxxo");
        }
    }

    private void descuentaPuntosCanje(Long idUsuario, int puntosDescontar) {
        var usuarioPuntos = this.usuarioPuntosColorEntityRepository.findByIdArqUsuario(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usted no tiene punto acumulados."));
        if (usuarioPuntos.getPuntos() < puntosDescontar) {
            throw new IllegalArgumentException("Sus puntos no son suficientes para el canje.");
        }
        usuarioPuntos.setPuntos(usuarioPuntos.getPuntos() - puntosDescontar);
        this.usuarioPuntosColorEntityRepository.save(usuarioPuntos);
    }
}
