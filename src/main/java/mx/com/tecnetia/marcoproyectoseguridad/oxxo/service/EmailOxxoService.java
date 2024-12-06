package mx.com.tecnetia.marcoproyectoseguridad.oxxo.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.util.Constantes;
import mx.com.tecnetia.orthogonal.dto.MailDTO;
import mx.com.tecnetia.orthogonal.utils.email.EmailOperationsThymeleafService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-06
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class EmailOxxoService {
    private final EmailOperationsThymeleafService emailOperationsThymeleafService;
    @Value("${server.servlet.context-path-images}")
    private String contextImages;

    @Async
    public void enviarEmail(@Email @NotBlank String email, @NotBlank String ticket, @NotBlank String transaction, String baseUrl) {
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
}
