package mx.com.tecnetia.orthogonal.utils.email;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties("email")
@Data
//@AllArgsConstructor
//@RequiredArgsConstructor
public class EmailPropertiesComponent {
    private String host;
    private String port;
    private String username;
    private String password;
}
