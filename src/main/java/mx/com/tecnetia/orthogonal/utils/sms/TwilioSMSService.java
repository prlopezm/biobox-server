package mx.com.tecnetia.orthogonal.utils.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.dto.sms.SMS;
import mx.com.tecnetia.orthogonal.utils.propiedades.PropiedadComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class TwilioSMSService {
    private final PropiedadComponent propiedadComponent;
    @Value("${SMS.TWILIO.ACCOUNT-SID}")
    private String ACCOUNT_SID;
    @Value("${SMS.TWILIO.AUTH-TOKEN}")
    private String AUTH_TOKEN;
    @Value("${SMS.TWILIO.FROM-NUMBER}")
    private String FROM_NUMBER;

    public String send(SMS sms) {
        log.info("Enviando {}", sms.toString());
        Message message = null;
        String result = "";
        var params = buildParametrosTwilio();
        try {
            Twilio.init(params.sid(), params.token());
            message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(params.fromNumber()), sms.getMessage()).create();
            result = message.getStatus().toString();
        } catch (Exception e) {
            log.info("Error en el envío de mensaje a Twilio: {}", e.getMessage());
            throw new IllegalStateException("Error en el envío de mensaje a Twilio: " + e);
        }
        log.info("Datos del SMS: {}", message);
        log.info("Datos del envío del SMS: {}", result);
        return message.getSid();
    }

    record ParametrosTwilio(String sid, String token, String fromNumber) {
    }

    private ParametrosTwilio buildParametrosTwilio() {
        var sid = this.propiedadComponent.getPropiedades()
                .stream().filter(p -> Objects.equals(p.getCodigo(), ACCOUNT_SID))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta el SID de Twilio"))
                .getValor();
        var token = this.propiedadComponent.getPropiedades()
                .stream().filter(p -> Objects.equals(p.getCodigo(), AUTH_TOKEN))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta el token de Twilio"))
                .getValor();
        var fromNumber = this.propiedadComponent.getPropiedades()
                .stream().filter(p -> Objects.equals(p.getCodigo(), FROM_NUMBER))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta el celular de origen de Twilio"))
                .getValor();
        return new ParametrosTwilio(sid, token, fromNumber);
    }
}
