package mx.com.tecnetia.marcoproyectoseguridad.oxxo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.response.ValidacionUsuarioResponseDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository.CanjeOxxoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.orthogonal.utils.propiedades.PropiedadComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-05
 */
@Service
@RequiredArgsConstructor
@Log4j2
class OxxoCommonsService {
    private final PropiedadComponent propiedadComponent;
    private final CanjeOxxoEntityRepository canjeOxxoEntityRepository;
    private final UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;

    @Value("${OXXO_URL_VALIDA_USUARIO}")
    private String urlValidaUsuarioPropiedad;
    @Value("${OXXO_PARAM_XAPIKEY}")
    private String keyPropiedad;
    @Value("${OXXO_PARAM_PARTNER_ID}")
    private String partnerIdPropiedad;
    @Value("${OXXO_CANJES_MENSUALES}")
    private String cantidadCanjesPropiedad;
    @Value("${OXXO_URL_CANJE}")
    private String urlCanjePuntosOxxoPropiedad;

    ValidacionUsuarioResponseDTO validarUsuario(String celular) {
        var paramLlamada = this.buildParametrosLlamadaOxxoValidaUsuario();
        var cel = celular.startsWith("+52") ? celular : "+52" + celular;
        var urlEndpoint = paramLlamada.url() + (cel);
        try {
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", paramLlamada.key());
            headers.set("partner-id", paramLlamada.partnerId());
            var requestEntity = new HttpEntity<>(headers);
            var responseData = restTemplate.exchange(urlEndpoint, HttpMethod.GET, requestEntity, ValidacionUsuarioResponseDTO.class);
            log.info("Recibimos de Oxxo: {}", responseData.getBody());
            return responseData.getBody();
        } catch (RestClientException e) {
            if (parserErrorOxxo(e.getMessage())) {
                log.error("Error: {}", e.getMessage());
                throw new IllegalStateException("Para acceder a los beneficios Spin, por favor regístrate https://spinpremia.com/");
            } else {
                log.error("No se pudo invocar al servicio de Oxxo: {}", e.getMessage());
                throw new IllegalStateException("Fallo al invocar al servicio de Oxxo");
            }
        }
    }

    ParametroLlamadaOxxo buildParametrosLlamadaOxxoValidaUsuario() {
        var url = this.propiedadComponent.getPropiedades()
                .stream()
                .filter(v -> Objects.equals(v.getCodigo(), this.urlValidaUsuarioPropiedad))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta la URL de validación de usuario de Oxxo"))
                .getValor();
        var comm = buildParametrosLlamadaComunes();
        return new ParametroLlamadaOxxo(url, comm.key(), comm.partnerId());
    }

    ParamatrosLlamadaComunes buildParametrosLlamadaComunes() {
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
        return new ParamatrosLlamadaComunes(key, partnerId);
    }

    private record ParamatrosLlamadaComunes(String key, String partnerId) {
    }

    ParametroLlamadaOxxo buildParametrosCanjePuntos() {
        var url = this.propiedadComponent.getPropiedades()
                .stream()
                .filter(v -> Objects.equals(v.getCodigo(), this.urlCanjePuntosOxxoPropiedad))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta la URL de canje de puntos de Oxxo"))
                .getValor();
        var comm = buildParametrosLlamadaComunes();
        return new ParametroLlamadaOxxo(url, comm.key, comm.partnerId);
    }

    List<PropiedadOxxo> propiedadesOxxo() {
        return this.propiedadComponent.getPropiedades()
                .stream()
                .filter(v -> Objects.equals(v.getGrupoCodigo(), "OXXO"))
                .map(prop -> new PropiedadOxxo(prop.getCodigo(), prop.getValor()))
                .toList();
    }

    boolean parserErrorOxxo(String error) {
        return error.contains("Member not found");
    }

    int cantidadPuntosUsuarioFirmado(Long usuarioId) {
        return this.usuarioPuntosColorEntityRepository.totalPuntosUsuario(usuarioId);
    }

    int cantidadCanjesRestantes(Long idUsuarioLogeado) {
        int cantidadMaximaCanjesMensuales = 0;
        try {
            cantidadMaximaCanjesMensuales = Integer.parseInt(this.propiedadComponent.getPropiedades()
                    .stream()
                    .filter(v -> Objects.equals(v.getCodigo(), (this.cantidadCanjesPropiedad)))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta la cantidad máxima de canjes mensuales."))
                    .getValor());
        } catch (NumberFormatException e) {
            log.error("No se puede obtener la cantidad máxima de reciclajes por mes desde la BD. El valor no es un entero");
            throw new IllegalStateException("No se puede obtener la cantidad máxima de reciclajes por mes desde la BD. El valor no es un entero");
        }
        var cantidadCanjesMesActual = this.canjeOxxoEntityRepository.canjesMesActualUsuario(idUsuarioLogeado);
        return Math.max(cantidadMaximaCanjesMensuales - cantidadCanjesMesActual, 0);
    }
}
