package mx.com.tecnetia.test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import mx.com.tecnetia.MarcoApplication;
import mx.com.tecnetia.marcoproyectoseguridad.service.BacardiService;
import mx.com.tecnetia.orthogonal.security.LoginUsuarioDTO;

@SpringBootTest(classes = MarcoApplication.class,  
		 webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "/application.properties")
class TestsProgramasLealtad {

	final String user = "113577";
	final String email = "egomez13k@gmail.com";
	final String telefono = "5565593909";	
	final String password = "kike";
	
	@Autowired
	private BacardiService bacardiService;
	
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
		
		token =	given()
					.body(user)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
					.when()
						.post(api)
					.then()
						.statusCode(equalTo(200))
						.extract().path("token");
		
		System.out.println("token: " + token);
		
		return token;
	}
	
	//@Test
	public void programasLealtad() throws JSONException {
		String api = "/programa-lealtad/puntos-programas-lealtad";
		String token = getTokenLogin();
		String mensaje = "";
		
		mensaje = given()
						.headers("Authorization", "Bearer " + token)	
						.when()
							.get(api)
						.then()
							.statusCode(equalTo(200))
							.contentType(MediaType.APPLICATION_JSON_VALUE)							
							.extract().path("programasLealtad").toString();
			
			System.out.println("mensaje: " + mensaje);	
	}	
	
	//@Test
	public void canjearProntiPago() throws JSONException {
		String api = "/programa-lealtad/canjear-programa";
		String token = getTokenLogin();
		String mensaje = "";
		
		mensaje = given()
						.headers("Authorization", "Bearer " + token)
							.param("sku", "S3AMAZONCASHMXN")
							.param("monto", 5000)
							.param("tipoPrograma", "p")
							.param("idPuntosRequeridos", 0)
						.when()
							.get(api)
						.then()
							.statusCode(equalTo(200))
							.contentType(MediaType.APPLICATION_JSON_VALUE)
							.body("mensaje", notNullValue())
							.extract().path("mensaje");
			
			System.out.println("mensaje: " + mensaje);	
	}
	
	//@Test
	public void canjearPrana() throws JSONException {
		String api = "/programa-lealtad/canjear-programa";
		String token = getTokenLogin();
		String mensaje = "";
			
		mensaje = given()
						.headers("Authorization", "Bearer " + token)
							.param("sku", "PRA01100NATURAL1")
							.param("monto", 0)
							.param("tipoPrograma", "r")
							.param("idPuntosRequeridos", 4)				
						.when()
							.get(api)
						.then()
							.statusCode(equalTo(200))
							.contentType(MediaType.APPLICATION_JSON_VALUE)
							.body("mensaje", notNullValue())
							.extract().path("mensaje");
				
		System.out.println("mensaje: " + mensaje);	
	}
	
	@Test
	public void canjearBacardi() throws JSONException {
		String api = "/programa-lealtad/canjear-programa";
		String token = getTokenLogin();
		String mensaje = "";
		
		mensaje = given()
						.headers("Authorization", "Bearer " + token)
							.param("sku", "B1DESCUENTO10")
							.param("monto", 0)
							.param("tipoPrograma", "b")
							.param("idPuntosRequeridos", 1)				
						.when()
							.get(api)
						.then()
							.statusCode(equalTo(200))
							.contentType(MediaType.APPLICATION_JSON_VALUE)
							.body("mensaje", notNullValue())
							.extract().path("mensaje");
			
			System.out.println("mensaje: " + mensaje);
	}
		
}
