package mx.com.tecnetia.test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import mx.com.tecnetia.MarcoApplication;
import mx.com.tecnetia.orthogonal.dto.EditaUsuarioArquitecturaDTO;
import mx.com.tecnetia.orthogonal.security.LoginUsuarioDTO;

@SpringBootTest(classes = MarcoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "/application.properties")
public class TestUsuario {	
	
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
	
	@Test
	public void modificaDatosUsuario() throws JSONException {
		String api = "/arq/usuario/edita";
		String token = getTokenLogin();
		String mensaje = "";
		
		EditaUsuarioArquitecturaDTO datos = new EditaUsuarioArquitecturaDTO();
		datos.setNombres("Kike");
		datos.setApellidoPaterno("Gómez");
		datos.setApellidoMaterno("P");
		datos.setNick("egomez13k@gmail.com");
		datos.setEmail("egomez13k@gmail.com");
		datos.setTelefono("55655939091");
		datos.setTelefonoValidado(true);
		datos.setEmailValidado(true);
    	
		mensaje = given().body(datos).headers("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON_VALUE)
					.when().patch(api).then().statusCode(equalTo(200))
					.contentType(MediaType.APPLICATION_JSON_VALUE).extract().path("mensaje").toString();
		
		System.out.println("mensaje: " + mensaje);
	}
	
}
