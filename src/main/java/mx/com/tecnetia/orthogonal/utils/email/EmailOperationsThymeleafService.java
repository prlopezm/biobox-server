package mx.com.tecnetia.orthogonal.utils.email;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.orthogonal.dto.MailDTO;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailOperationsThymeleafService {
    private final JavaMailSender sender;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    public void sendEmail(MailDTO mailDTO, String templateThymeleafFile) throws MessagingException {
        var thymeleafContext = new Context();
        thymeleafContext.setVariables(mailDTO.getModel());
        var mimeMessage = sender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        String htmlBody = thymeleafTemplateEngine.process(templateThymeleafFile, thymeleafContext);
        var mailToOrig = StringUtils.deleteWhitespace(mailDTO.getMailTo());
        try {
            InternetAddress.parse(mailToOrig);
        } catch (AddressException addressException) {
        	log.error(addressException);
            throw new IllegalArgumentException("Las direcciones de email siguientes son inválidas: " + mailToOrig);
        }
        if (mailToOrig.contains(",")) {
            helper.setTo(StringUtils.split(mailToOrig, ','));
        } else {
            helper.setTo(mailToOrig);
        }
        helper.setText(htmlBody, true);
        helper.setSubject(mailDTO.getMailSubject());
        sender.send(mimeMessage);
        log.info("Email enviado correctamente.");
    }
}
