package mx.com.tecnetia.test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import mx.com.tecnetia.MarcoApplication;
import mx.com.tecnetia.orthogonal.security.LoginUsuarioDTO;

@SpringBootTest(classes = MarcoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "/application.properties")
public class TestLogin {	
	
	final String user = "113577";
	final String email = "egomez13k@gmail.com";
	final String telefono = "5565593909";	
	final String password = "kike";
	
	@BeforeAll
	public static void setup() {
		baseURI = "http://localhost:8080";
		basePath = "/biobox";
	}
	
	//@Test
	public void login() {
		String api = "/arq/free/login";
		String mensaje = "";
		LoginUsuarioDTO user = new LoginUsuarioDTO();
		user.setNick(this.email);
		user.setPassw(this.password);

		mensaje = given().body(user).contentType(MediaType.APPLICATION_JSON_VALUE).when().post(api).then()
				.statusCode(equalTo(200)).extract().path("mensaje");

		System.out.println("mensaje: " + mensaje);

	}
	
}
