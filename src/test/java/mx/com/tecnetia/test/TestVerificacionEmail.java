package mx.com.tecnetia.test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import mx.com.tecnetia.MarcoApplication;
import mx.com.tecnetia.marcoproyectoseguridad.dto.sms.VerificacionEmailDTO;
import mx.com.tecnetia.orthogonal.security.LoginUsuarioDTO;
import mx.com.tecnetia.orthogonal.utils.EstatusProcesoEnum;

@SpringBootTest(classes = MarcoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "/application.properties")
public class TestVerificacionEmail {	

	final String user = "113577";
	final String email = "egomez13k@gmail.com";
	final String telefono = "5565593909";	
	final String password = "kike";
	
	@BeforeAll
	public static void setup() {
		baseURI = "http://localhost:8080";
		basePath = "/biobox";
	}

	private String getTokenLogin() {
		String api = "/arq/free/login";
		String token = "";
		LoginUsuarioDTO user = new LoginUsuarioDTO();
		user.setNick(this.email);
		user.setPassw(this.password);

		token = given().body(user).contentType(MediaType.APPLICATION_JSON_VALUE).when().post(api).then()
				.statusCode(equalTo(200)).extract().path("token");

		System.out.println("token: " + token);

		return token;
	}
	
	//@Test
	public void enviaCodigoDeVerificacion() throws JSONException {
		String api = "verificacion/email/codigo/envia";
		String token = getTokenLogin();
		String mensaje = "";

		mensaje = given().headers("Authorization", "Bearer " + token).param("email", this.email).when().get(api).then().statusCode(equalTo(200))
				.contentType(MediaType.APPLICATION_JSON_VALUE).extract().path("mensaje").toString();

		System.out.println("mensaje: " + mensaje);		
	}
	
	@Test
	public void validaCodigoDeVerificacion() throws JSONException {
		String api = "verificacion/email/codigo/envia";
		String api2 = "verificacion/email/codigo/valida";
		String token = getTokenLogin();
		String codigo = "";
		VerificacionEmailDTO codigoDTO = new VerificacionEmailDTO();
		
		codigo = given().headers("Authorization", "Bearer " + token).param("email", this.email).when().get(api).then().statusCode(equalTo(200))
				.contentType(MediaType.APPLICATION_JSON_VALUE).extract().path("data").toString();		
		System.out.println("Codigo enviado: "+ codigo);	
		
		//Valida codigo correcto
		codigoDTO.setEmail(this.email);
		codigoDTO.setCodigo(codigo);
		given().body(codigoDTO).headers("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON_VALUE)
		.when().post(api2).then().statusCode(equalTo(200)).assertThat().body(containsString(EstatusProcesoEnum.EXITOSO.getValue()));
				
		//Valida codigo no existente
		codigoDTO.setCodigo("123456");
		given().body(codigoDTO).headers("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON_VALUE)
		.when().post(api2).then().statusCode(equalTo(400)).assertThat().body(containsString("El código es inválido"));
			
		//Valida codigo null
		codigoDTO.setCodigo(null);
		given().body(codigoDTO).headers("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON_VALUE)
		.when().post(api2).then().statusCode(equalTo(400));
				
	}
	
	//@Test
	public void enviaLinkDeVerificacion() throws JSONException {
		String api = "verificacion/email/link/envia";
		String token = getTokenLogin();
		String mensaje = "";

		 String email = "egomez13k@gmail.com";
		mensaje = given().headers("Authorization", "Bearer " + token).param("email", email).when().get(api).then().statusCode(equalTo(200))
				.contentType(MediaType.APPLICATION_JSON_VALUE).extract().path("mensaje").toString();

		System.out.println("mensaje: " + mensaje);
	}
		
	//@Test
	public void validaLinkDeVerificacion_success() throws JSONException {				
		String api = "verificacion/email/link/envia";
		String api2 = "verificacion/email/link/valida";		
		String codigoEncriptado = "";
		
		String token = getTokenLogin();
		
		//Envia link		
		codigoEncriptado = given().headers("Authorization", "Bearer " + token).param("email", this.email).when().get(api).then().statusCode(equalTo(200))
				.contentType(MediaType.APPLICATION_JSON_VALUE).extract().path("data").toString();	
		System.out.println("codigo: " + codigoEncriptado);
		//Valida link
		given().headers("Authorization", "Bearer " + token).param("t", codigoEncriptado).when().get(api2).then().statusCode(equalTo(200))
		.assertThat().body(containsString("El email con que se registró acaba de ser confirmado"));
		
	}
	
	//@Test
	public void validaLinkDeVerificacion_error() throws JSONException {		
		String api = "verificacion/email/link/valida"; //Codigo no encontrado
		String codigo1 = "Bxdip99WxvU8srWUyck2yJyItIoK6fWyS3REw9I=";
		String api2 = "verificacion/email/link/valida"; //Tag mismatch!
		String codigo2 = "w1O4KRNbTMrPmjNyDlgNmHSVKOi9yw/BVq3z/yA=";
		
		String token = getTokenLogin();		
		given().headers("Authorization", "Bearer " + token).param("t", codigo1).when().get(api).then().statusCode(equalTo(200))
		.assertThat().body(containsString("El email con que se registró no pudo ser confirmado"));
		given().headers("Authorization", "Bearer " + token).param("t", codigo2).when().get(api2).then().statusCode(equalTo(200))
		.assertThat().body(containsString("El email con que se registró no pudo ser confirmado"));		
	}

}
