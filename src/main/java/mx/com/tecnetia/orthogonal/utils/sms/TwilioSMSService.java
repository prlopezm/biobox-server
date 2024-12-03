package mx.com.tecnetia.orthogonal.utils.sms;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.dto.sms.SMS;

@Service
@RequiredArgsConstructor
@Log4j2
public class TwilioSMSService{
	
	@Value("${sms.twilio.account-sid}")
	private String ACCOUNT_SID = "AC2824a42ed7200acbae8c232bae6955e0";
	@Value("${sms.twilio.auth-token}")
	private String AUTH_TOKEN = "82908fa3954a98b74b8b238148965ca3";
	@Value("${smd.twilio.from-number}")
	private String FROM_NUMBER = "+15093090507";   
    
	public String send(SMS sms) {
		log.info("Enviando {}", sms.toString());
		Message message = null;
		String result = "";
		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	        message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage()).create();
	        result = message.getStatus().toString();
		}catch (Exception e) {			
			e.printStackTrace();
			//return null;
		}
        log.info("Sms enviado getStatus: {}", message.getStatus().toString());
        log.info("Sms enviado getBody: {}", message.getBody());        
        log.info("Sms enviado getPrice: {}", message.getPrice());
        log.info("Sms enviado getPriceUnit: {}", message.getPriceUnit());
        log.info("Sms enviado getDateSent: {}", message.getDateSent());
        log.info("Sms enviado getErrorCode: {}", message.getErrorCode());
        log.info("Sms enviado getErrorMessage: {}", message.getErrorMessage());        
		return message.getSid();
	}
}
