package mx.com.tecnetia.marcoproyectoseguridad.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeBase64Util {
	
	
	public static String encodeString(String key) {
		String result = Base64.getUrlEncoder().encodeToString(key.getBytes(StandardCharsets.UTF_8));
		return result;		
	}
	
	public static String decodeString(String key) {
		String result = new String(Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8)));
		return result;
	}	
	

}
