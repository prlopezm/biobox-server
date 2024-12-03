package mx.com.tecnetia.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;

import mx.com.tecnetia.marcoproyectoseguridad.util.EncodeBase64Util;

public class TestDecode {
	
	@Test
	public void codificarUrl() throws MalformedURLException
	{
		String token = "?t=U0E+/4wV2v5mxePOmz/4XfVsrgbHF0QqY5LJd5nuZTsADY4k0KRX4Rxa";
		String encodeToken = EncodeBase64Util.encodeString(token);		
		System.out.println("Token Codificado: " + encodeToken);
		String decodeToken = EncodeBase64Util.decodeString(encodeToken);		
		System.out.println("Token Decodificado: " + decodeToken);
		assertEquals(token, decodeToken);		
	}
	
}
