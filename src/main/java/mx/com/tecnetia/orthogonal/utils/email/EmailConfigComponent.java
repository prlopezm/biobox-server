package mx.com.tecnetia.orthogonal.utils.email;

import lombok.AllArgsConstructor;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqPropiedadEntityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Configuration
@AllArgsConstructor
public class EmailConfigComponent {
    private final ArqPropiedadEntityRepository arqPropiedadEntityRepository;
    private final Environment environment;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        String emailHost = environment.getProperty("email.host");
        String emailPassword = environment.getProperty("email.password");
        String emailUsername = environment.getProperty("email.username");
        String emailPort = environment.getProperty("email.port");
        String mailSmtpAuth = environment.getProperty("mail.smtp.auth");
        String mailTransportProtocol = environment.getProperty("mail.transport.protocol");
        String mailDebug = environment.getProperty("mail.debug");
        String mailSmtpStarttlsEnable = environment.getProperty("mail.smtp.starttls.enable");

        mailSender.setHost(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(emailHost)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + emailHost)).getValor());
        mailSender.setPort(Integer.parseInt(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(emailPort)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: "+ emailPort)).getValor()));
        mailSender.setUsername(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(emailUsername)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: "+emailUsername)).getValor());
        mailSender.setPassword(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(emailPassword)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + emailPassword)).getValor());

        Properties javaMailProperties = new Properties();

        var ent = this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(mailSmtpStarttlsEnable)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + mailSmtpStarttlsEnable));
        javaMailProperties.put(ent.getCodigo(), ent.getValor());
        ent = this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(mailSmtpAuth)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: "+ mailSmtpAuth));
        javaMailProperties.put(ent.getCodigo(), ent.getValor());
        ent = this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(mailTransportProtocol)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: "+ mailTransportProtocol));
        javaMailProperties.put(ent.getCodigo(), ent.getValor());
        ent = this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(mailDebug)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: "+ mailDebug));
        javaMailProperties.put(ent.getCodigo(), ent.getValor());

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
}
