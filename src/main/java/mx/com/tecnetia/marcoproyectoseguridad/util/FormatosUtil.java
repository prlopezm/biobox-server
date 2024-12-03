package mx.com.tecnetia.marcoproyectoseguridad.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatosUtil {
	
	public static boolean numeroTelefonoValido(String telefono) {
		Pattern pat = Pattern.compile(Constantes.REGEX_TELEFONO);
		Matcher mat = pat.matcher(telefono);
		if (mat.matches())
			return true;
		else
			return false;	
	}
	
	public static boolean direccionEmailValida(String email) {
		Pattern pat = Pattern.compile(Constantes.REGEX_EMAIL);
		Matcher mat = pat.matcher(email);
		if (mat.matches())
			return true;
		else
			return false;	
	}

}
