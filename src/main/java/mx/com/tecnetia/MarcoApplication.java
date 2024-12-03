package mx.com.tecnetia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@SpringBootApplication
@EnableAsync
/*@OpenAPIDefinition(info = @Info(title = "Marco de proyecto", version = "1.0", description = """
        Documentación del API del proyecto Marco.<br>
        <b>NOTA IMPORTANTE:</b><br>
        El header 'Authorization' debe tener como valor 'Bearer TOKEN',<br>
        donde 'TOKEN' es la cadena encriptada devuelta por el servicio de login."""))*/
@OpenAPIDefinition(
        info = @Info(
                title = "BioBox",
                version = "${api.version}",
                contact = @Contact(
                        name = "prlopezm", email = "pablo@tecnetia.com.mx", url = "https://tecnetia.com.mx"
                ),
                license = @License(
                        name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        )

)
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.HTTP, scheme = "Bearer")
public class MarcoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MarcoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MarcoApplication.class);
    }

}
