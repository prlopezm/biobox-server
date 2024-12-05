package mx.com.tecnetia.marcoproyectoseguridad.oxxo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.MenuOxxoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.response.ValidacionUsuarioResponseDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity.OxxoMemberIdEntity;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository.CanjeOxxoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository.OpcionCanjeOxxoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository.OxxoMemberIdEntityRepository;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import mx.com.tecnetia.orthogonal.utils.propiedades.PropiedadComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
public class MenuOxxoServiceImpl implements MenuOxxoService {
    private final PropiedadComponent propiedadComponent;
    private final UsuarioService usuarioService;
    private final OpcionCanjeOxxoEntityRepository opcionCanjeOxxoEntityRepository;
    private final CanjeOxxoEntityRepository canjeOxxoEntityRepository;
    private final OxxoMemberIdEntityRepository oxxoMemberIdEntityRepository;

    @Value("${OXXO_URL_VALIDA_USUARIO}")
    private String urlPropiedad;
    @Value("${OXXO_URL_VALIDA_USUARIO_KEY}")
    private String keyPropiedad;
    @Value("${OXXO_URL_VALIDA_USUARIO_PARTNER_ID}")
    private String partnerIdPropiedad;
    @Value("${OXXO_CANJES_MENSUALES}")
    private String cantidadCanjesPropiedad;
    @Value("${OXXO_LEYENDA}")
    private String leyendaPropiedad;

    @Override
    @Transactional
    public MenuOxxoDTO obtenerMenu() {
        var usuarioLogeado = this.usuarioService.getUsuarioLogeado();
        var usuarioValido = this.validarUsuario(usuarioLogeado.getTelefono());
        if (Objects.equals(usuarioValido.getStatus(), "success")) {
            var opciones = this.opcionCanjeOxxoEntityRepository.getAllDTO();
            var menu =  new MenuOxxoDTO()
                    .setLeyenda(leyendaOxxo())
                    .setPuntosRestantes(cantidadCanjesRestantes(usuarioLogeado.getIdArqUsuario()))
                    .setOpciones(opciones);
            this.actualizaMemberId(usuarioLogeado.getIdArqUsuario(),usuarioValido.getData().getMember_id());
            return menu;
        } else {
            throw new IllegalStateException("Para acceder a los beneficios Spin, por favor regístrate https://spinpremia.com/\"");
        }
    }


    private ValidacionUsuarioResponseDTO validarUsuario(String celular) {
        var paramLlamada = this.buildParametrosLlamadaOxxo();
        var urlEndpoint = paramLlamada.url + celular;
        try {
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", paramLlamada.key);
            headers.set("partner-id", paramLlamada.partnerId);
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

    private void actualizaMemberId(Long usuarioLogueadoId, String memberId) {
        var ent = this.oxxoMemberIdEntityRepository.findByArqUsuarioId(usuarioLogueadoId)
                .orElse(new OxxoMemberIdEntity()
                        .setArqUsuarioId(usuarioLogueadoId)
                        .setMemberId(memberId));
        this.oxxoMemberIdEntityRepository.save(ent);
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

    /*
     * Devuelve la cantidad de canjes que le quedan al usuario logeado en el mes en curso
     * */
    private int cantidadCanjesRestantes(Long idUsuarioLogeado) {
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

    private String leyendaOxxo() {
        return this.propiedadComponent.getPropiedades()
                .stream()
                .filter(v -> Objects.equals(v.getCodigo(), (this.leyendaPropiedad)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta la leyenda de Oxxo."))
                .getValor();
    }

    private boolean parserErrorOxxo(String error) {
        return error.contains("Member not found");
    }
}
