package mx.com.tecnetia.orthogonal.utils.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.orthogonal.dto.MailDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Log4j2
public class EMailOperationsFreeMarkerService {
    private final JavaMailSender sender;
    private final Configuration freemarkerConfig;

    @Async
    public void sendEmail(MailDTO mailDTO, String templateFreeMarkerFile) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Template template = freemarkerConfig.getTemplate(templateFreeMarkerFile);
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailDTO.getModel());
        var mailToOrig = StringUtils.deleteWhitespace(mailDTO.getMailTo());

        if (mailToOrig.contains(",")) {
            helper.setTo(StringUtils.split(mailToOrig, ','));
        } else {
            helper.setTo(mailToOrig);
        }

        helper.setText(text, true);
        helper.setSubject(mailDTO.getMailSubject());

        sender.send(message);
        log.info("Email bienvenida enviado");
    }
}
