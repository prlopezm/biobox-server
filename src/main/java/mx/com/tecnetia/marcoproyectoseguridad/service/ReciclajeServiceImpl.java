package mx.com.tecnetia.marcoproyectoseguridad.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.QuioscoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.DiaReciclajeBloqueadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.*;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.*;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.*;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.template.ProductoRecicladoJdbcRepository;
import mx.com.tecnetia.marcoproyectoseguridad.util.CodigoRespuestaMaquinaEnum;
import mx.com.tecnetia.orthogonal.dto.MailDTO;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqPropiedadEntityRepository;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqUsuarioRepository;
import mx.com.tecnetia.orthogonal.utils.email.EmailOperationsThymeleafService;
import mx.com.tecnetia.orthogonal.utils.quioscos.TipoPicEnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReciclajeServiceImpl implements ReciclajeService {

    @jakarta.annotation.Resource
    @Lazy
    private ReciclajeServiceImpl reciclajeServiceImpl;

    private final ProductoReciclableEntityRepository productoReciclableEntityRepository;
    private final ProductoRecicladoEntityRepository productoRecicladoEntityRepository;
    private final ProductoRecicladoJdbcRepository productoRecicladoJdbcRepository;
    private final ArqUsuarioRepository arqUsuarioRepository;
    private final FotoProductoRecicladoEntityRepository fotoProductoRecicladoEntityRepository;
    private final PesoProductoRecicladoEntityRepository pesoProductoRecicladoEntityRepository;
    private final ProductoReciclableColorPuntosEntityRepository productoReciclableColorPuntosEntityRepository;
    private final UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;
    private final QuioscoEntityRepository quioscoEntityRepository;
    private final ProductoRecicladoQuioscoEntityRepository productoRecicladoQuioscoEntityRepository;
    private final Environment environment;
    private final ConfiguracionMovimientoCharolaQuioscoEntityRepository configuracionMovimientoCharolaQuioscoEntityRepository;
    private final EmailOperationsThymeleafService emailOperationsThymeleafService;
    private final UsuarioPuntosColorAcumuladoEntityRepository usuarioPuntosColorAcumuladoEntityRepository;
    private final ArqPropiedadEntityRepository arqPropiedadEntityRepository;
    private final ConcurrentHashMap<Long, Date> quioscosEnUso = new ConcurrentHashMap<>();
    private final UnidadMedidaEntityRepository unidadMedidaEntityRepository;
    private final CapacidadEntityRepository capacidadEntityRepository;
    private final MaterialEntityRepository materialEntityRepository;
    private final SubMarcaEntityRepository subMarcaEntityRepository;
    private final FabricanteEntityRepository fabricanteEntityRepository;
    private final ColorEntityRepository colorEntityRepository;

    private void enviarMailLlenado(List<QuioscoEntity> quioscos) {
        String begin = "<tr>" +
                "               <td colspan='4' style='background-color:white; height:25px;'>" +
                "                  <span>";
        String end = "</span>" +
                "               </td>" +
                "            </tr>";
        String maquinasListHtml = "";

        for (QuioscoEntity quiosco : quioscos) {
            maquinasListHtml = begin + quiosco.getDireccion() + end;
        }

        var model = new HashMap<String, Object>();
        model.put("maquinasList", maquinasListHtml);
        var templateThymeleafFile = "maquina-llena-thymeleaf.html";
        var mailDTO = new MailDTO();
        mailDTO.setMailTo("carlos@tecnetia.com.mx");
        mailDTO.setMailSubject("BioBox - Máquina Llena");
        mailDTO.setModel(model);
        try {
            this.emailOperationsThymeleafService.sendEmail(mailDTO, templateThymeleafFile);
        } catch (Exception ex) {
            log.error("Ocurrió un error al enviar el email de quioscos llenos: {}", ex.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Scheduled(cron = "10 0 0 * * *")
    public void validaLlenadoMaquinas() {
        String urlServicio = environment.getProperty("maquina.service.url.llenado");
        List<QuioscoEntity> quioscos = this.quioscoEntityRepository.findAll();
        List<QuioscoEntity> quioscosLlenos = new ArrayList<QuioscoEntity>();
        for (QuioscoEntity quiosco : quioscos) {
            String urlEndpoint = "http://" + quiosco.getIp() + ":8080" + urlServicio;

            var restTemplate = new RestTemplate();
            try {
                var headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");
                var requestEntity = new HttpEntity<>(quiosco.getTipoArduino() ? TipoPicEnum.ARDUINO.getTipoPic() : TipoPicEnum.PLC.getTipoPic(), headers);
                Integer alturaActual = restTemplate.postForObject(urlEndpoint, requestEntity, Integer.class);
                if (quiosco.getAlturaLlenadoMm() != null) {
                    if (alturaActual.intValue() <= quiosco.getAlturaLlenadoMm().intValue()) {
                        quioscosLlenos.add(quiosco);
                    }
                }
            } catch (RestClientException e) {
                log.error("Error al consultar el nivel de llenado de la máquina " + quiosco.getIp() + ": {}", e.getMessage());
            }
        }

        if (quioscosLlenos.size() > 0)
            this.enviarMailLlenado(quioscosLlenos);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean usuarioPuedeReciclar(Long idUsuario) {
        java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());

        //se valida si no ha cumplido con el maximo de reciclajes por dia
        String maxReciclajesDiariosCve = this.environment.getProperty("reciclaje.max.reciclajes.diarios");
        Integer maxReciclajesDiarios = Integer.valueOf(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(maxReciclajesDiariosCve)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + maxReciclajesDiariosCve)).getValor());
        List<ProductoRecicladoEntity> productosRecicladosHoy = this.productoRecicladoEntityRepository.getByFechaAndUsuario(idUsuario, hoy);
        if (productosRecicladosHoy.size() >= maxReciclajesDiarios) {
            throw new IllegalArgumentException("No puedes realizar más reciclajes este día. Continúa reciclando mañana.");
        }

        //se valida si no ha sobrepasado el numero de dias con bloqueos por fallas
        String maxFallasDiariasCve = this.environment.getProperty("reciclaje.max.fallas.diarias");
        Integer maxFallasDiarias = Integer.valueOf(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(maxFallasDiariasCve)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + maxFallasDiariasCve)).getValor());
        String maxDiasParaBloqueoCve = this.environment.getProperty("reciclaje.max.dias.fallas.para.bloqueo");
        Integer maxDiasParaBloqueo = Integer.valueOf(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(maxDiasParaBloqueoCve)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + maxDiasParaBloqueoCve)).getValor());
        List<DiaReciclajeBloqueadoDTO> diasReciclajesBloaqueados = this.productoRecicladoJdbcRepository.getDiasReciclajesBloaqueados(idUsuario, maxFallasDiarias);
        if (diasReciclajesBloaqueados.size() >= maxDiasParaBloqueo) {
            throw new IllegalArgumentException("Has sobrepasado el número de bloqueos por reciclar productos que no son válidos. Ya no puedes seguir reciclando.");
        }

        //se validan los reciclajes fallidos que lleva en el dia
        List<ProductoRecicladoEntity> reciclajesFallidosHoy = this.productoRecicladoEntityRepository.getByFechaAndExitosoAndUsuario(idUsuario, hoy, false);
        if (reciclajesFallidosHoy.size() >= maxFallasDiarias) {
            throw new IllegalArgumentException("No puedes realizar más reciclajes este día debido a que has introducido muchos productos que no son válidos. Continúa reciclando mañana.");
        }

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public QuioscoDTO buscaMaquina(String qr, Long idUsuarioLogeado) {

        if (this.usuarioPuedeReciclar(idUsuarioLogeado)) {

            Optional<QuioscoDTO> quiosco = this.quioscoEntityRepository.getByQr(UUID.fromString(qr));
            if (!quiosco.isPresent()) {
                throw new IllegalArgumentException("No encontramos el QR en nuestro catálogo de máquinas. Intenta de nuevo.");
            }

            return quiosco.get();
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoAReciclarDTO getProductoAReciclar(String barCode, Long idUsuarioLogeado) {

        if (this.usuarioPuedeReciclar(idUsuarioLogeado)) {
            Optional<ProductoAReciclarDTO> productoOpt = this.productoReciclableEntityRepository.getProductoByBarCode(barCode);

            if (!productoOpt.isPresent()) {
                log.error("Error al encontrar código de barras en la BD: " + barCode);
            }

            if (!productoOpt.isPresent()) {
                var msg = "No encontramos el código de barras " + barCode + " en nuestro catálogo de productos a reciclar. Intenta de nuevo.";
                throw new IllegalArgumentException(msg);
            }
            return productoOpt.get();
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoAReciclarDTO> getListaProductosAReciclar(String barCodes, Long idUsuarioLogeado) {
        List<String> codes = Arrays.stream(barCodes.split(",")).toList();
        if (this.usuarioPuedeReciclar(idUsuarioLogeado)) {
            List<ProductoAReciclarDTO> productos = this.productoReciclableEntityRepository.getProductosByBarCodes(codes);
            if (productos.isEmpty()) {
                log.error("Error al encontrar los códigos de barras en la BD: " + codes);
                var msg = "No encontramos los códigos de barras " + codes + " en nuestro catálogo de productos a reciclar. Intenta de nuevo.";
                throw new IllegalArgumentException(msg);
            }
            return productos;
        }
        return null;
    }

    @Synchronized
    private void tomarQuiosco(Long idQuiosco, Long idUsuario) {
        Date fechaActual = new Date();

        if (!this.quioscosEnUso.containsKey(idQuiosco)) {
            log.info("Inicia reciclaje concurrente en quiosco {}, para usuario {}", idQuiosco, idUsuario);
            this.quioscosEnUso.put(idQuiosco, fechaActual);
        } else {
            Date fechaTomaQuiosco = this.quioscosEnUso.get(idQuiosco);
            long segundosDif = (fechaActual.getTime() - fechaTomaQuiosco.getTime()) / 1000;
            if (segundosDif >= 30) {
                this.quioscosEnUso.remove(idQuiosco);
                log.info("Inicia reciclaje concurrente en quiosco {}, para usuario {}", idQuiosco, idUsuario);
                this.quioscosEnUso.put(idQuiosco, fechaActual);
            } else {
                log.warn("Falla reciclaje concurrente en quiosco {} , usuario: ", idQuiosco, idUsuario);
                log.info("Hashmap falla de reciclaje Quiosco: {}", this.quioscosEnUso);
                throw new IllegalArgumentException("No se puede realizar el reciclaje porque la máquina está ocupada. Espera a que termine de reciclar.");
            }
        }
        log.info("Hashmap Tomar Quiosco: {}", this.quioscosEnUso);
    }

    @Synchronized
    private void liberarQuiosco(Long idQuiosco, Long idUsuarioLogeado) {
        log.info("Termina reciclaje concurrente en quiosco {}, usuario {}", idQuiosco, idUsuarioLogeado);
        this.quioscosEnUso.remove(idQuiosco);
        log.info("Hashmap liberar quiosco: {}", this.quioscosEnUso);
    }

    @Override
    public Boolean quioscoEstaEnUso(Long idQuiosco) {
        log.info("**** QUIOSCO ESTA EN USO? {}", this.quioscosEnUso);
        if (this.quioscosEnUso == null) {
            return false;
        }
        log.info("**** QUIOSCO ESTA EN USO 2? {}", this.quioscosEnUso.containsKey(idQuiosco));
        if (this.quioscosEnUso.containsKey(idQuiosco)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void estresarQuiosco(Long idQuiosco) {
        Long idUsuario = 14l;
        String barCode = "75007614";
        int i = 0;
        while (true) {
            try {
                log.info("Enviando proceso a estresar: {}", i);
                this.enviarProcesoDeReciclajeEnQuiosco(idUsuario, barCode, idQuiosco);
                log.info("Se envió proceso a estresar: {}", i);
                try {
                    Thread.sleep(25000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
                i = i + 1;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public ProductoAReciclarDTO enviarProcesoDeReciclajeEnQuiosco(Long idUsuarioLogeado, String barCode, Long idQuiosco) {
        log.info("Tomando quiosco *********************");
        this.tomarQuiosco(idQuiosco, idUsuarioLogeado);

        if (this.usuarioPuedeReciclar(idUsuarioLogeado)) {

            QuioscoEntity quioscoEntity = this.quioscoEntityRepository.findById(idQuiosco)
                    .orElseThrow(() -> new IllegalArgumentException("No encontramos la máquina en nuestro catálogo. Intenta de nuevo."));
            int tipoQuiosco = quioscoEntity.getTipoArduino() ? TipoPicEnum.ARDUINO.getTipoPic() : TipoPicEnum.PLC.getTipoPic();

            Optional<ProductoAReciclarDTO> productoOptional = this.productoReciclableEntityRepository.getProductoByBarCode(barCode);
            ProductoAReciclarDTO producto = null;
            if (productoOptional.isPresent()) {
                producto = productoOptional.get();
            }

            if (tipoQuiosco == TipoPicEnum.ARDUINO.getTipoPic() && productoOptional.isEmpty()) {
                log.warn("El código de barras {} no está dado de alta en la BD", barCode);
                reciclajeServiceImpl.enviaEmailSKUNotFound(barCode);
                var ent = this.guardaNuevoProductoReciclableNOTFOUND(barCode);
                producto = getProductoAReciclarDTO(ent);
            }

            //TODO: Es probable que esta sección y la anterior puedan unirse.
            if (tipoQuiosco == TipoPicEnum.PLC.getTipoPic() && productoOptional.isEmpty()) {
                var ent = this.guardaNuevoProductoReciclableNOTFOUND(barCode);
                //En esta sección, antes buscaba el producto con código de barras NOT_FOUND y usaba ese
                log.warn("No encontramos el código de barras {} en nuestro catálogo de productos a reciclar.", barCode);
                producto = getProductoAReciclarDTO(ent);
                //Enviar email con el SKU que no se encontró:
                reciclajeServiceImpl.enviaEmailSKUNotFound(barCode);
            }

            List<ConfiguracionMovimientoCharolaQuioscoEntity> configCharolaEntity =
                    this.configuracionMovimientoCharolaQuioscoEntityRepository.getByIdQuioscoAndIdMaterial(idQuiosco, producto.getIdMaterial());
            boolean moverALaDerecha = true;
            if (!configCharolaEntity.isEmpty()) {
                if (configCharolaEntity.get(0).getMovimientoCharolaByIdMovimientoCharola() != null) {
                    if (!configCharolaEntity.get(0).getMovimientoCharolaByIdMovimientoCharola().getCodigo().equals("DER")) {
                        moverALaDerecha = false;
                    }
                }
            }

            /* INVOCA SERVICIO DE QUIOSCO PARA EMPEZAR A RECICLAR */
            String ip = quioscoEntity.getIp();
            String urlServerMaquina = "http://" + ip + ":8080";
            String urlEndpoint = urlServerMaquina + environment.getProperty("maquina.service.url.reciclar");

            var restTemplate = new RestTemplate();
            try {
                ReciclaProductoQuioscoRequestDTO reciclaProductoQuioscoRequestDTO = new ReciclaProductoQuioscoRequestDTO();
                reciclaProductoQuioscoRequestDTO.setIdQuiosco(idQuiosco);
                reciclaProductoQuioscoRequestDTO.setBarCode(barCode);
                reciclaProductoQuioscoRequestDTO.setIdUsuario(idUsuarioLogeado);
                reciclaProductoQuioscoRequestDTO.setIdProducto(producto.getIdProducto());
                reciclaProductoQuioscoRequestDTO.setMoverCharolaALaDerecha(moverALaDerecha);
                reciclaProductoQuioscoRequestDTO.setTipoPic(tipoQuiosco);
                var headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");
                var requestEntity = new HttpEntity<>(reciclaProductoQuioscoRequestDTO, headers);
                restTemplate.postForObject(urlEndpoint, requestEntity, Void.class);
            } catch (RestClientException e) {
                log.error("Error al enviar el proceso de reciclaje a la máquina: {}", e.getMessage());
                throw new IllegalArgumentException("No se pudo iniciar el proceso de reciclaje en la máquina. Intenta de nuevo.");
            }
            log.info("Termina envio de reciclaje");

            return producto;
        }

        return null;
    }

    @Override
    @Transactional
    public List<ProductoAReciclarDTO> enviarProcesosDeReciclajeEnQuiosco(Long idUsuarioLogeado, String barCodes, Long idQuiosco) {
        log.info("Tomando quiosco V2 *********************");
        this.tomarQuiosco(idQuiosco, idUsuarioLogeado);

        if (this.usuarioPuedeReciclar(idUsuarioLogeado)) {

            QuioscoEntity quioscoEntity = this.quioscoEntityRepository.findById(idQuiosco)
                    .orElseThrow(() -> new IllegalArgumentException("No encontramos la máquina en nuestro catálogo. Intenta de nuevo."));
            int tipoQuiosco = quioscoEntity.getTipoArduino() ? TipoPicEnum.ARDUINO.getTipoPic() : TipoPicEnum.PLC.getTipoPic();

            List<ProductoAReciclarDTO> productos = new ArrayList<>();
            List<String> codes = Arrays.stream(barCodes.split(",")).toList();
            for (String code: codes) {
                Optional<ProductoAReciclarDTO> productoOptional = this.productoReciclableEntityRepository.getProductoByBarCode(code);
                ProductoAReciclarDTO producto = null;
                if (productoOptional.isPresent()) {
                    producto = productoOptional.get();
                }

                if (productoOptional.isEmpty()) {
                    log.warn("El código de barras {} no está dado de alta en la BD", code);
                    reciclajeServiceImpl.enviaEmailSKUNotFound(code);
                    var ent = this.guardaNuevoProductoReciclableNOTFOUND(code);
                    producto = getProductoAReciclarDTO(ent);
                }
                productos.add(producto);
            }

            /* INVOCA SERVICIO DE QUIOSCO PARA EMPEZAR A RECICLAR */
            String ip = quioscoEntity.getIp();
            String urlServerMaquina = "http://" + ip + ":8080";
            String urlEndpoint = urlServerMaquina + environment.getProperty("maquina.service.url.reciclar");

            var restTemplate = new RestTemplate();
            try {
                ReciclaProductoQuioscoRequestV2DTO reciclaProductoQuioscoRequestDTO = new ReciclaProductoQuioscoRequestV2DTO();
                reciclaProductoQuioscoRequestDTO.setIdQuiosco(idQuiosco);
                reciclaProductoQuioscoRequestDTO.setBarCode(barCodes);
                reciclaProductoQuioscoRequestDTO.setIdUsuario(idUsuarioLogeado);
                reciclaProductoQuioscoRequestDTO.setIdsProducto(productos.stream().map(ProductoAReciclarDTO::getIdProducto).toList());
                reciclaProductoQuioscoRequestDTO.setMoverCharolaALaDerecha(true);
                reciclaProductoQuioscoRequestDTO.setTipoPic(tipoQuiosco);
                var headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");
                var requestEntity = new HttpEntity<>(reciclaProductoQuioscoRequestDTO, headers);
                restTemplate.postForObject(urlEndpoint, requestEntity, Void.class);
            } catch (RestClientException e) {
                log.error("Error al enviar el proceso de reciclaje a la máquina: {}", e.getMessage());
                throw new IllegalArgumentException("No se pudo iniciar el proceso de reciclaje en la máquina. Intenta de nuevo.");
            }

            log.info("Termina envio de reciclaje");

            return productos;
        }

        return null;
    }

    private static ProductoAReciclarDTO getProductoAReciclarDTO(@NotNull ProductoReciclableEntity ent) {
        var producto = new ProductoAReciclarDTO();
        producto.setIdProducto(ent.getIdProductoReciclable());
        producto.setNombre(ent.getBarCode());
        producto.setIdMaterial(ent.getIdMaterial());
        return producto;
    }

    @Async
    public void enviaEmailSKUNotFound(String sku) {
        var mailAddress = this.arqPropiedadEntityRepository
                .findArqPropiedadEntitiesByActivoIsTrueAndCodigo("email.sku.notfound")
                .orElseThrow(() -> new IllegalStateException("La BD contiene un error. Por favor, contacte al soporte técnico de la aplicación, " +
                        "reporte este error: property_email.sku.notfound_" + sku + " y recibirás 50 puntos BioBox."))
                .getValor();
        var model = new HashMap<String, Object>();
        model.put("sku", sku);
        var templateThymeleafFile = "email-sku-not-found.html";
        var mailDTO = new MailDTO();
        mailDTO.setMailTo(mailAddress);
        mailDTO.setMailSubject("SKU no encontrado");
        mailDTO.setModel(model);
        try {
            this.emailOperationsThymeleafService.sendEmail(mailDTO, templateThymeleafFile);
        } catch (Exception ex) {
            log.error("Ocurrió un error al enviar el email SKU not found: {}", ex.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void enviarNotificacionReciclajeTerminado(ReciclajeTerminadoRequestDTO reciclajeTerminadoRequestDTO) {
        log.info("Liberando quiosco {}*********************", reciclajeTerminadoRequestDTO.getIdQuiosco());
        this.liberarQuiosco(reciclajeTerminadoRequestDTO.getIdQuiosco(), reciclajeTerminadoRequestDTO.getIdUsuario());

        /*NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setTitle("");
        DataDTO dataDTO = new DataDTO();

        dataDTO.setStep(StepEnum.RECICLAJE_TERMINO.getStep());
        notificationDTO.setBody("Excelente! Gracias por colaborar al reciclaje. Se validará el producto y se acumularán tus puntos.");

        TokenCelEntity tokenCelEntity = this.tokenCelEntityRepository.findById(reciclajeTerminadoRequestDTO.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("El token del dispositivo del usuario no se encuentra en la BD."));
        FirebaseNotificationDTO firebaseNotificationDTO = new FirebaseNotificationDTO();
        firebaseNotificationDTO.setNotification(notificationDTO);
        firebaseNotificationDTO.setTo(tokenCelEntity.getToken());
        firebaseNotificationDTO.setData(dataDTO);

        this.firebaseNotificationsService.sendNotification(firebaseNotificationDTO);*/
        log.info("Termina liberación del quiosco {}", reciclajeTerminadoRequestDTO.getIdQuiosco());

    }

    @Override
    @Transactional
    public void reciclaProductoEnQuioscoConPeso(Long idUsuario, Long idProducto, Long idQuiosco, Integer codigoRespuesta, Integer peso) {
        log.info("Entrando a reciclaProductoEnQuioscoConPeso");
        boolean productoValido = true;

        ProductoRecicladoEntity productoReciclado = null;
        ProductoReciclableEntity productoReciclable = this.productoReciclableEntityRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("El producto no se encuentra en la BD."));

        if (!Objects.equals(productoReciclable.getSku(), "NOT_FOUND") &&
                ((new BigDecimal(peso).compareTo(productoReciclable.getPesoMinimo()) < 0)
                        || (new BigDecimal(peso).compareTo(productoReciclable.getPesoMaximo()) > 0))) {
            productoValido = false;
        }
        if ((new BigDecimal(peso).compareTo(productoReciclable.getPesoMinimo()) < 0)
                || (new BigDecimal(peso).compareTo(productoReciclable.getPesoMaximo()) > 0)) {
            productoValido = false;
        }
        log.info("Producto: {}. Peso recibido: {}. Rango de pesos: entre {} y {}. Es válido: {}. Código de respuesta RECICLAJE_EXITOSO: {}",
                productoReciclable.getSku(), peso, productoReciclable.getPesoMinimo(), productoReciclable.getPesoMaximo(), productoValido,
                codigoRespuesta.intValue() == CodigoRespuestaMaquinaEnum.RECICLAJE_EXITOSO.getCodigoRespuesta());

        productoReciclado = this.guardarReciclaje(productoReciclable, idUsuario, idQuiosco, codigoRespuesta, productoValido);

        PesoProductoRecicladoEntity pesoProducto = new PesoProductoRecicladoEntity();
        pesoProducto.setProductoRecicladoByIdProductoReciclado(productoReciclado);
        pesoProducto.setPeso(peso);
        pesoProducto.setExitoso(productoValido);
        pesoProducto = this.pesoProductoRecicladoEntityRepository.save(pesoProducto);

        log.info("Termina guardado de reciclaje");
        log.info("Saliendo de reciclaProductoEnQuioscoConPeso");
    }

    private void saveFoto(byte[] foto, String nombreFoto) {
        try{
            var path = Paths.get(nombreFoto);
            Files.write(path,foto);
        } catch (IOException e) {
            log.error("Error al guardar foto {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @Override
    @Transactional
    public void reciclaProductoEnQuioscoConFoto(@NotNull Long idUsuario, @NotNull Long idProducto, @NotNull Long idQuiosco,
                                                Integer codigoRespuesta, @NotBlank String foto, @NotBlank String barcode) {
        var productoValido = true;
        String label = "";
        Long idProductoReciclado = 0L;
        ProductoRecicladoEntity productoReciclado = null;
        //ProductoReciclableEntity productoReciclable = null;
        byte[] fotoByte = Base64.getDecoder().decode(foto);
        //TODO: Salvado temporal para revisar foto. Evaluar si borramos para ir a producción
        saveFoto(fotoByte,"C:/pics/fotosreciclaje/"+String.valueOf(idQuiosco)
                .concat("-")
                .concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")))+".jpg");
        var productoReciclable = this.productoReciclableEntityRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalStateException("El producto reciclable no se encuentra en la BD."));
        //Antes, cuando era posible que el producto no existiera en la BD, aquí lo guardaba
        //Cuando el producto no está en la BD. idProductoReciclado=0L. Buscar qué pasa con el algoritmo. Tengo que meterlo en la BD como producto NOT_FOUND
        //productoReciclable = productoReciclableOpt.orElseGet(() -> this.guardaNuevoProductoReciclableNOTFOUND(barcode));
        label = productoReciclable.getSubMarcaByIdSubMarca().getNombre();
        productoReciclado = this.guardarReciclaje(productoReciclable, idUsuario, idQuiosco, codigoRespuesta, productoValido);
        idProductoReciclado = productoReciclado.getIdProductoReciclado();

        var fotoProducto = new FotoProductoRecicladoEntity();
        fotoProducto.setProductoRecicladoByIdProductoReciclado(productoReciclado);
        fotoProducto.setFoto(fotoByte);
        fotoProducto.setExitoso(productoValido);
        this.fotoProductoRecicladoEntityRepository.save(fotoProducto);

        try {
            log.info("Llamando a validaProductoFoto. idProductoReciclado: {}, barcode: {}, label: {}", idProductoReciclado, barcode, label);
            //TODO:Dos líneas comentadas, para que siempre el producto sea válido, esto hasta probar el algoritmo:
/*            productoValido = this.validaProductoFoto(idProductoReciclado, barcode, label, foto);
            productoReciclado.setExitoso(productoValido);*/
            productoValido = true;
            productoReciclado.setExitoso(productoValido);
            this.productoRecicladoEntityRepository.save(productoReciclado);
        } catch (Exception e) {
            log.error(e);
            productoValido = false;
        }
        log.info("Termina guardado de reciclaje con foto.");
    }

    @Override
    @Transactional
    public void reciclaProductoEnQuioscoArduino(@NotNull Long idUsuario, @NotNull List<Long> idsProducto, @NotNull Long idQuiosco,
                                                Integer codigoRespuesta, @NotBlank String barcode) {

        var productoValido = true;
        String label = "";
        Long idProductoReciclado = 0L;
        ProductoRecicladoEntity productoReciclado = null;

        var productosReciclable = this.productoReciclableEntityRepository.findByIds(idsProducto);

        if (productosReciclable.isEmpty()) {
            throw new IllegalStateException("El producto reciclable no se encuentra en la BD.");
        }

        for (ProductoReciclableEntity productoReciclable: productosReciclable) {
            label = productoReciclable.getSubMarcaByIdSubMarca().getNombre();
            productoReciclado = this.guardarReciclaje(productoReciclable, idUsuario, idQuiosco, codigoRespuesta, productoValido);
            idProductoReciclado = productoReciclado.getIdProductoReciclado();

            try {
                log.info("Guardando producto. idProductoReciclado: {}, barcode: {}, label: {}", idProductoReciclado, barcode, label);
                productoReciclado.setExitoso(productoValido);
                this.productoRecicladoEntityRepository.save(productoReciclado);
            } catch (Exception e) {
                log.error(e);
            }
            log.info("Termina guardado de reciclaje en Arduino.");
        }
    }


    private ProductoReciclableEntity guardaNuevoProductoReciclableNOTFOUND(String barCode) {
        var entOpt = this.productoReciclableEntityRepository.findBySku(barCode);
        if (entOpt.isPresent()) {
            return entOpt.get();
        }
        var ent = new ProductoReciclableEntity();
        var cap = this.capacidadEntityRepository.findAll().get(0);
        var mat = this.materialEntityRepository.findByNombre("NOT_FOUND")
                .orElseThrow(() -> new IllegalStateException("No se ha configurado la BD para material NOT_FOUND."));
        var subMarca = this.subMarcaEntityRepository.findByNombre("NOT_FOUND")
                .orElseThrow(() -> new IllegalStateException("No se ha configurado la BD para sub marcas NOT_FOUND."));
        var fabricante = this.fabricanteEntityRepository.findByNombre("NOT_FOUND")
                .orElseThrow(() -> new IllegalStateException("No se ha configurado la BD para fabricante NOT_FOUND."));

        ent.setBarCode(barCode)
                .setCapacidadByIdCapacidad(cap)
                .setMaterialByIdMaterial(mat)
                .setPesoMaximo(new BigDecimal(2000))
                .setPesoMinimo(new BigDecimal(1))
                .setSku(barCode)
                .setSubMarcaByIdSubMarca(subMarca)
                .setFabricante(fabricante);
        ent = this.productoReciclableEntityRepository.save(ent);
        log.info("Guardando producto reciclable NOT_FOUND automáticamente: {}", ent);
        var color = this.colorEntityRepository.findAll().get(0);
        var puntos = new ProductoReciclableColorPuntosEntity()
                .setColorByIdColor(color)
                .setFechaFin(null)
                .setFechaInicio(Timestamp.valueOf(LocalDateTime.now()))
                .setProdReciclableByIdProdReciclable(ent)
                .setPuntos(1);
        this.productoReciclableColorPuntosEntityRepository.save(puntos);
        return ent;
    }

    @Override
    @Transactional(readOnly = false)
    public MensajeDTO<?> reciclaProducto(Long idUsuarioLogeado, Long idProducto, byte[] foto) {
        boolean productoValido = true;
        ProductoRecicladoEntity productoReciclado = null;
        ProductoReciclableEntity productoReciclable = this.productoReciclableEntityRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("El producto no se encuentra en la BD."));

        productoValido = this.validaAlgoritmoProducto(idProducto, foto);

        if (productoValido) {
            productoReciclado = this.guardarReciclaje(productoReciclable, idUsuarioLogeado, 0L,
                    CodigoRespuestaMaquinaEnum.RECICLAJE_EXITOSO.getCodigoRespuesta(),
                    productoValido);
            FotoProductoRecicladoEntity fotoProducto = new FotoProductoRecicladoEntity();
            fotoProducto.setProductoRecicladoByIdProductoReciclado(productoReciclado);
            fotoProducto.setFoto(foto);
            fotoProducto.setExitoso(productoValido);
            fotoProducto = this.fotoProductoRecicladoEntityRepository.save(fotoProducto);
        } else {
            throw new IllegalArgumentException("La foto del producto no es correcta. Intenta de nuevo o prueba con otro producto.");
        }

        return new MensajeDTO<>("Muchas gracias por ayudar! Tu producto será reciclado y tus puntos BioBox han sido acumulados.");
    }

    //    @Transactional(readOnly = false)
    private ProductoRecicladoEntity guardarReciclaje(ProductoReciclableEntity productoReciclable, Long idUsuario, Long idQuiosco, Integer codigoRespuesta, boolean productoValido) {
        Integer puntos = 0;
        ArqUsuarioEntity usuario = this.arqUsuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no pudo encontrarse en la BD."));
        log.info("Usuario reciclando: {}. El producto es válido: {}", usuario.getEmail(), productoValido);
        Long idProductoReciclable = productoReciclable != null ? productoReciclable.getIdProductoReciclable() : 0L;

        ProductoRecicladoEntity productoReciclado = new ProductoRecicladoEntity();
        productoReciclado.setMomentoReciclaje(Timestamp.valueOf(LocalDateTime.now()));
        productoReciclado.setIdProductoReciclable(idProductoReciclable);
        productoReciclado.setProductoReciclableByIdProductoReciclable(productoReciclable);
        productoReciclado.setArqUsuarioByIdArqUsuario(usuario);

        if (codigoRespuesta.intValue() == CodigoRespuestaMaquinaEnum.RECICLAJE_EXITOSO.getCodigoRespuesta()) {
            log.info("Entrando a validar reciclaje Exitoso");
            productoReciclado.setExitoso(productoValido);
            productoReciclado = this.productoRecicladoEntityRepository.save(productoReciclado);

            List<ProductoReciclableColorPuntosEntity> productoReciclableList = this.productoReciclableColorPuntosEntityRepository.getByIdProductoReciclableAndFechaFinIsNull(idProductoReciclable);
            log.info("Producto reciclable: " + idProductoReciclable + "  --  " + productoReciclableList.size());

            for (ProductoReciclableColorPuntosEntity productoReciclableColor : productoReciclableList) {
                Optional<UsuarioPuntosColorEntity> usuarioPuntosOpt = this.usuarioPuntosColorEntityRepository.findByIdArqUsuarioAndIdColor(usuario.getIdArqUsuario(), productoReciclableColor.getIdColor());
                if (usuarioPuntosOpt.isPresent()) {
                    UsuarioPuntosColorEntity usuarioPuntos = usuarioPuntosOpt.get();
                    log.info("Actualizar usuario existente puntos nuevo: " + productoReciclableColor.getPuntos() + " :: " + usuarioPuntos.getIdArqUsuario() + " :: " + usuarioPuntos.getIdColor());
                    if (productoValido) {
                        puntos = usuarioPuntos.getPuntos() + productoReciclableColor.getPuntos();
                    } else {
                        //TODO: Para la prueba en OXXO comenté esta línea, para que siempre sumara puntos
                        //puntos = usuarioPuntos.getPuntos();
                        puntos = usuarioPuntos.getPuntos() + productoReciclableColor.getPuntos();
                    }
                    log.info("El usuario {} se le dan {} puntos. Tenía antes: {}", usuario.getEmail(), puntos, usuarioPuntos.getPuntos());
                    usuarioPuntos.setPuntos(puntos);
                    this.usuarioPuntosColorEntityRepository.save(usuarioPuntos);
                } else {
                    log.info("Crear usuario puntos nuevo: " + productoReciclableColor.getPuntos() + " :: " + usuario.getIdArqUsuario() + " :: " + productoReciclableColor.getIdColor());
                    UsuarioPuntosColorEntity usuarioPuntos = new UsuarioPuntosColorEntity();
                    puntos = productoReciclableColor.getPuntos();
                    log.info("Al usuario {} se le dan {} puntos. No tenía puntos.", usuario.getEmail(), puntos);
                    usuarioPuntos.setPuntos(puntos);
                    usuarioPuntos.setArqUsuarioByIdArqUsuario(usuario);
                    ColorEntity colorEntity = new ColorEntity();
                    colorEntity.setIdColor(productoReciclableColor.getIdColor());
                    usuarioPuntos.setColorByIdColor(colorEntity);
                    usuarioPuntos = this.usuarioPuntosColorEntityRepository.save(usuarioPuntos);
                }

                //guardamos el histórico de movimientos
                UsuarioPuntosColorAcumuladoEntity usuarioPuntosAcumulado = new UsuarioPuntosColorAcumuladoEntity();
                usuarioPuntosAcumulado.setPuntos(productoReciclableColor.getPuntos());
                usuarioPuntosAcumulado.setColorByIdColor(productoReciclableColor.getColorByIdColor());
                usuarioPuntosAcumulado.setProductoRecicladoByIdProductoReciclado(productoReciclado);
                this.usuarioPuntosColorAcumuladoEntityRepository.save(usuarioPuntosAcumulado);
            }
        } else {
            log.info("El reciclaje no fue exitoso");
            productoReciclado.setExitoso(false);
            productoReciclado = this.productoRecicladoEntityRepository.save(productoReciclado);
        }

        if (idQuiosco != 0L) {
            ProductoRecicladoQuioscoEntity productoRecicladoQuioscoEntity = new ProductoRecicladoQuioscoEntity();
            productoRecicladoQuioscoEntity.setIdProductoReciclado(productoReciclado.getIdProductoReciclado());
            productoRecicladoQuioscoEntity.setIdQuiosco(idQuiosco);
            productoRecicladoQuioscoEntity = this.productoRecicladoQuioscoEntityRepository.save(productoRecicladoQuioscoEntity);
        }

        return productoReciclado;
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeDTO<?> validaProducto(Long idProducto, byte[] foto) {
        var productoValido = this.validaAlgoritmoProducto(idProducto, foto);

        if (!productoValido) {
            throw new IllegalArgumentException("La foto del producto no es correcta. Intenta de nuevo o prueba con otro producto.");
        }

        return new MensajeDTO<>("");
    }

    private boolean validaProductoFoto(@NotNull Long idProductoReciclado, @NotBlank String barcode, String label,
                                       @NotBlank String image) {
        String urlEndpoint = environment.getProperty("service.url.clasificador.imagenes");
        //byte[] imageBase64 = Base64.getDecoder().decode(image);
        boolean productoValido = false;
        RequestValidaProductoFotoDTO requestDTO = new RequestValidaProductoFotoDTO();
        requestDTO.setIdProductoReciclado(idProductoReciclado);
        requestDTO.setClase(barcode);
        requestDTO.setLabel(label);
//        requestDTO.setImageBase64(imageBase64);
        requestDTO.setImageBase64(image);
        log.info("Enviando al clasificador. label: {}, idProductoReciclado: {}, clase: {}, tamaño imagen: {}",
                requestDTO.getLabel(), requestDTO.getIdProductoReciclado(), requestDTO.getClase(), requestDTO.getImageBase64().length());

        try {
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            var requestEntity = new HttpEntity<>(requestDTO, headers);
            var responseData = restTemplate.postForObject(urlEndpoint, requestEntity, ResponseValidaProductoFotoDTO.class);
            productoValido = responseData.getData().isUserResponse();
            log.info("Respuesta de la IA: {} ", responseData.getData());
            //TODO: Guardar en una tabla la respuesta de la IA
        } catch (RestClientException e) {
            log.error("No se pudo invocar al servicio de reconocimiento de imágenes: {}", e.getMessage());
            productoValido = false;
        }

        return productoValido;
    }

    @Override
    @Transactional(readOnly = true)
    public MensajeDTO<?> validaProductoCodigoBarras(Long idProducto, String foto) {
        var productoValido = this.validaCodigoBarrasProducto(idProducto, foto);

        if (!productoValido) {
            throw new IllegalArgumentException("El código de barras de la foto del producto no es correcto. Intenta de nuevo o prueba con otro producto.");
        }

        return new MensajeDTO<>("");
    }

    @Transactional(readOnly = true)
    private boolean validaCodigoBarrasProducto(Long idProducto, String foto) {
        var producto = this.productoReciclableEntityRepository.findById(idProducto);
        String barcode = producto.isPresent() ? producto.get().getBarCode() : "";
        String urlEndpoint = environment.getProperty("service.url.obtener.codigo.barras");
        boolean productoValido = false;
        try {
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            var requestCodigoBarrasDTO = new RequestCodigoBarrasDTO();
            requestCodigoBarrasDTO.setImage(foto);
            var requestEntity = new HttpEntity<>(requestCodigoBarrasDTO, headers);
            ResponseCodigoBarrasDTO responseEntity = restTemplate.postForObject(urlEndpoint, requestEntity, ResponseCodigoBarrasDTO.class);

            log.info("Codigo de Barras del producto: {}", barcode);
            for (String barCodeTmp : responseEntity.getBarcodes()) {
                log.info("Codigo de Barras encontrado: {}", barCodeTmp);
                if (barcode.equals(barCodeTmp)) {
                    productoValido = true;
                    break;
                }
            }
        } catch (RestClientException e) {
            log.error("No se pudo invocar al servicio para obtener el codigo de barras de la foto: " + e.getMessage());
            return false;
        }

        return productoValido;
    }

    @Transactional(readOnly = true)
    private boolean validaAlgoritmoProducto(Long idProducto, byte[] foto) {
        var producto = this.productoReciclableEntityRepository.findById(idProducto);
        String barcode = producto.isPresent() ? producto.get().getBarCode() : "";
        String urlEndpoint = environment.getProperty("service.url.validation.photo");
        ResponseValidaFotoDTO responseValidaFotoDTO = new ResponseValidaFotoDTO();
        boolean productoValido = false;
        try {
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", getTestFile(foto));
            body.add("barcode", barcode);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<ResponseValidaFotoDTO> responseEntity = restTemplate.postForEntity(urlEndpoint, requestEntity, ResponseValidaFotoDTO.class);

            responseValidaFotoDTO = responseEntity.getBody();

            if (responseValidaFotoDTO.getRealibilityScore().compareTo(new BigDecimal(0.2)) >= 0) {
                productoValido = true;
            }
        } catch (RestClientException e) {
            log.error("No se pudo invocar al servicio de validacion de la foto: " + e.getMessage());
            return false;
        } catch (IOException e) {
            log.error("No se pudo invocar al servicio de validacion de la foto: " + e.getMessage());
            return false;
        }

        return productoValido;
    }

    @Override
    @Transactional
    public void reciclaProductoEnQuioscoPlc(Long idUsuario, List<Long> idsProducto, Long idQuiosco, Integer codigoRespuesta, Integer peso) {
        log.info("Entrando a reciclaProductoEnQuioscoConPeso");
        boolean productoValido = true;

        ProductoRecicladoEntity productoReciclado = null;
        List<ProductoReciclableEntity> productosReciclable = this.productoReciclableEntityRepository.findByIds(idsProducto);
        if (productosReciclable.isEmpty()) {
            throw new IllegalArgumentException("Los productos no se encuentran en la BD.");
        }

        for (ProductoReciclableEntity productoReciclable: productosReciclable) {
            if (!Objects.equals(productoReciclable.getSku(), "NOT_FOUND") &&
                    ((new BigDecimal(peso).compareTo(productoReciclable.getPesoMinimo()) < 0)
                            || (new BigDecimal(peso).compareTo(productoReciclable.getPesoMaximo()) > 0))) {
                productoValido = false;
            }
            if ((new BigDecimal(peso).compareTo(productoReciclable.getPesoMinimo()) < 0)
                    || (new BigDecimal(peso).compareTo(productoReciclable.getPesoMaximo()) > 0)) {
                productoValido = false;
            }
            log.info("Producto: {}. Peso recibido: {}. Rango de pesos: entre {} y {}. Es válido: {}. Código de respuesta RECICLAJE_EXITOSO: {}",
                    productoReciclable.getSku(), peso, productoReciclable.getPesoMinimo(), productoReciclable.getPesoMaximo(), productoValido,
                    codigoRespuesta.intValue() == CodigoRespuestaMaquinaEnum.RECICLAJE_EXITOSO.getCodigoRespuesta());

            productoReciclado = this.guardarReciclaje(productoReciclable, idUsuario, idQuiosco, codigoRespuesta, productoValido);

            PesoProductoRecicladoEntity pesoProducto = new PesoProductoRecicladoEntity();
            pesoProducto.setProductoRecicladoByIdProductoReciclado(productoReciclado);
            pesoProducto.setPeso(peso);
            pesoProducto.setExitoso(productoValido);
            pesoProducto = this.pesoProductoRecicladoEntityRepository.save(pesoProducto);

            log.info("Termina guardado de reciclaje");
            log.info("Saliendo de reciclaProductoEnQuioscoConPeso");
        }
    }

    private static Resource getTestFile(byte[] file) throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        Files.write(testFile, file);
        return new FileSystemResource(testFile.toFile());
    }

}
