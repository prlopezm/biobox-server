package mx.com.tecnetia.marcoproyectoseguridad.oxxo.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.ResponseValidaProductoFotoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.MenuOxxoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.response.ValidacionUsuarioResponseDTO;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import mx.com.tecnetia.orthogonal.utils.propiedades.PropiedadComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-03
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class OxxoServiceImpl implements OxxoService {
    private final PropiedadComponent propiedadComponent;
    private final UsuarioService usuarioService;
    @Value("${OXXO_URL_VALIDA_USUARIO}")
    private String urlPropiedad;
    @Value("${OXXO_URL_VALIDA_USUARIO_KEY}")
    private String keyPropiedad;
    @Value("${OXXO_URL_VALIDA_USUARIO_PARTNER_ID}")
    private String partnerIdPropiedad;

    @Override
    @Transactional(readOnly = true)
    public MenuOxxoDTO obtenerMenu() {
        var usuarioLogeado = this.usuarioService.getUsuarioLogeado();
        return null;
    }

    @Override
    @Transactional
    public String canjearPuntos(@NotNull Integer id) {
        return "";
    }

    private ValidacionUsuarioResponseDTO usuarioRegistrado(String celular) {
        var paramLlamada = this.buildParametrosLlamadaOxxo();
        var urlEndpoint = paramLlamada.url + celular;
        try {
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key",paramLlamada.key);
            headers.set("partner-id",paramLlamada.partnerId);
            var requestEntity = new HttpEntity<>(headers);
            var responseData = restTemplate.postForObject(urlEndpoint, requestEntity, ValidacionUsuarioResponseDTO.class);
            return responseData;
        } catch (RestClientException e) {
            log.error("No se pudo invocar al servicio de Oxxo: {}", e.getMessage());
            throw new IllegalStateException("Fallo al invocar al servicio de Oxxo");
        }
    }


    private ParametroLlamadaOxxo buildParametrosLlamadaOxxo() {
        var url = this.propiedadComponent.getPropiedades()
                .stream()
                .filter(v -> Objects.equals(v.getCodigo(), this.urlPropiedad))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta la URL de oxxo"))
                .getValor();
        var key = this.propiedadComponent.getPropiedades()
                .stream()
                .filter(v -> Objects.equals(v.getCodigo(), this.keyPropiedad))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta el API KEY de oxxo"))
                .getValor();
        var partnerId = this.propiedadComponent.getPropiedades()
                .stream()
                .filter(v -> Objects.equals(v.getCodigo(), (this.partnerIdPropiedad)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta el PARTNER ID de oxxo"))
                .getValor();
        return new ParametroLlamadaOxxo(url, key, partnerId);
    }

    private record ParametroLlamadaOxxo(String url, String key, String partnerId) {
    }
}
