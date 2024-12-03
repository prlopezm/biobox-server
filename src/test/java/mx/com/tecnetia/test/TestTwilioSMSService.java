package mx.com.tecnetia.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import mx.com.tecnetia.MarcoApplication;
import mx.com.tecnetia.marcoproyectoseguridad.dto.sms.SMS;
import mx.com.tecnetia.orthogonal.utils.PaisEnum;
import mx.com.tecnetia.orthogonal.utils.sms.TwilioSMSService;

@SpringBootTest(classes = MarcoApplication.class)
@TestPropertySource(locations = "/application.properties")
public class TestTwilioSMSService {
	
	@Autowired
	private TwilioSMSService twilioSMSService;
	
	@Test
	public void sendSMS() {
		String result = null;
		SMS sms = new SMS(PaisEnum.MEXICO.getCodigo() + "7716775164", "Hola");
		result = twilioSMSService.send(sms);
		System.out.println(result);
	}

}
