package mx.com.tecnetia.marcoproyectoseguridad.util;

public interface Constantes {
	
	/*LOGO*/
	public static final String LOGO_BIOBOX_EMAIL = "/biobox.png";
	
	/*REGEX*/
	public static final String REGEX_TELEFONO = "[0-9]{10}";
	public static final String REGEX_EMAIL = "^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$";

	/*PLANTILLAS*/
	
	public static final String PLANTILLA_EMAIL_DESCUENTO_POR_IMAGEN = "email-canje-descuento-por-imagen-thymeleaf.html";
	public static final String PLANTILLA_EMAIL_DESCUENTO_POR_CODIGO = "email-canje-descuento-por-codigo-thymeleaf.html";
	public static final String PLANTILLA_EMAIL_CONFIRMACION_POR_LINK = "email-confirmacion-por-link-thymeleaf.html";
	public static final String PLANTILLA_EMAIL_CONFIRMACION_POR_CODIGO = "email-confirmacion-por-codigo-thymeleaf.html";
	
	public static final String PLANTILLA_HTML_LINK_CONFIRMACION_VALIDO = "email-confirmacion-link-valido-thymeleaf.html";
	public static final String PLANTILLA_HTML_LINK_CONFIRMACION_INVALIDO = "email-confirmacion-link-invalido-thymeleaf.html";
	
}
