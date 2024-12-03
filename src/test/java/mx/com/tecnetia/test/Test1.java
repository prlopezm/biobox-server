package mx.com.tecnetia.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import mx.com.tecnetia.marcoproyectoseguridad.util.Constantes;

public class Test1 {	
		
	//@Test
	public void validaRegexTelefono() {
		String numero = "0123456789";	
		Pattern pat = Pattern.compile(Constantes.REGEX_TELEFONO);
		Matcher mat = pat.matcher(numero);
		if (mat.matches()) {
			System.out.println("Numero de telefono valido");
		} else {
			System.out.println("Numero de telefono no valido");
		}
	}
	
	@Test
	public void validaRegexEmail() {
		String email = "0123456789@hotmail.com";	
		Pattern pat = Pattern.compile(Constantes.REGEX_EMAIL);
		Matcher mat = pat.matcher(email);
		if (mat.matches()) {
			System.out.println("Direccion de email valida");
		} else {
			System.out.println("Direccion de email no valida");
		}
	}
	
}
