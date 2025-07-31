package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.*;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappCategoriaEstadoEntity;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappEntity;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappUsadaEntity;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.CuponerappCategoriaEstadoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.CuponerappEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.CuponerappUsadaEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.util.Constantes;
import mx.com.tecnetia.marcoproyectoseguridad.util.FormatosUtil;
import mx.com.tecnetia.orthogonal.ampq.ActualizaPuntosEventoProducer;
import mx.com.tecnetia.orthogonal.dto.MailDTO;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import mx.com.tecnetia.orthogonal.utils.email.EmailOperationsThymeleafService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-17
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class CuponeraServiceImpl implements CuponeraService {
    private final CuponerappEntityRepository cuponerappEntityRepository;
    private final UsuarioService usuarioService;
    private final UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;
    private final CuponerappUsadaEntityRepository cuponerappUsadaEntityRepository;
    private final CuponerappCategoriaEstadoEntityRepository cuponerappCategoriaEstadoEntityRepository;
    private final EmailOperationsThymeleafService emailOperationsThymeleafService;

    private final ActualizaPuntosEventoProducer actualizaPuntosEventoProducer;

    @Override
    @Transactional(readOnly = true)
    public List<CuponDTO> getCupones() {
        var idArqUsuario = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        var puntosUsuario = this.usuarioPuntosColorEntityRepository.totalPuntosUsuario(idArqUsuario)
                .orElse(0);
        return this.cuponerappEntityRepository.getCupones();
                /*.stream()
                .filter(elem -> elem.getPuntos() <= puntosUsuario)
                .toList();*/
    }

    @Override
    @Transactional
    public RespuestaCuponCanjeadoDTO canjear(RespuestaCuponeraDTO respuestaCuponeraDTO) {
        var cupon = this.cuponerappEntityRepository.findByIdPromocion(respuestaCuponeraDTO.getPromoId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el cupón especificado."));
        var idUsuarioFirmado = this.usuarioService.getUsuarioLogeado()
                .getIdArqUsuario();
        var puntosRestantes = descuentaPuntosCanje(idUsuarioFirmado, cupon.getPuntos());
        enviarEmailConfirmacionCupon(this.usuarioService.getUsuarioLogeado().getEmail(),
                cupon, respuestaCuponeraDTO);
        guardaHistorico(cupon, idUsuarioFirmado, respuestaCuponeraDTO);
        var msg = String.format("Se descontaron %1$d puntos. Te quedan %2$d puntos restantes", cupon.getPuntos(), puntosRestantes);
        return new RespuestaCuponCanjeadoDTO(msg, puntosRestantes);
    }

    private void enviarEmailConfirmacionCupon(String email, CuponerappEntity cupon, RespuestaCuponeraDTO respuestaCuponeraDTO) {
        email = email.replace(" ", "").toLowerCase();
        //email="@gmail.com";
        log.info("enviarEmailConfirmacionCupon: " + email);

        if (!FormatosUtil.direccionEmailValida(email)) {
            throw new IllegalArgumentException("La dirección de correo no tiene un formato valido.");
        }

        try {
            var mailDTO = getMailDTO(email, cupon, respuestaCuponeraDTO);
            if(cupon.getIdCompra() == 2){
                this.emailOperationsThymeleafService.sendEmail(mailDTO, Constantes.PLANTILLA_HTML_CONFIRMACION_CUPON_2);
            }else{
                this.emailOperationsThymeleafService.sendEmail(mailDTO, Constantes.PLANTILLA_HTML_CONFIRMACION_CUPON_1);
            }
            log.info("Email enviado correctamente");
        } catch (Exception ex) {
            log.error("Ocurrió un error al enviar el email de confirmacion de canje: {}", ex.getMessage());
            ex.printStackTrace();
            throw new IllegalArgumentException("Error al enviar correo de confirmacion de canje");
        }
    }

    private MailDTO getMailDTO(String email, CuponerappEntity cupon, RespuestaCuponeraDTO respuestaCuponeraDTO) {
        var model = new HashMap<String, Object>();
        model.put("url_cupon", cupon.getUrl());
        model.put("folio", respuestaCuponeraDTO.getFolio());
        if(cupon.getIdCompra()!=2){
            model.put("fecha_canje", respuestaCuponeraDTO.getFecha() + " " +
                    (respuestaCuponeraDTO.getHora().contains("--") ? "" : respuestaCuponeraDTO.getHora()));
        }
        model.put("puntos", cupon.getPuntos());
        model.put("promocion", cupon.getPromocion());
        model.put("vigencia", new SimpleDateFormat("dd/MM/yy").format(cupon.getFechaVigencia()));
        model.put("descripcion", cupon.getDescripcion());

        var mailDTO = new MailDTO();
        mailDTO.setMailTo(email);
        mailDTO.setMailSubject("BioBox - Confirmacion de canje");
        mailDTO.setModel(model);
        return mailDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuponCanjeadoDTO> cuponesCanjeadosEntreFechas(LocalDate fechaInicial, LocalDate fechaFinal) {
        var idUsuarioFirmado = this.usuarioService.getUsuarioLogeado()
                .getIdArqUsuario();
        return this.cuponerappUsadaEntityRepository.usadosEntreFechasDeUsuario(fechaInicial.atStartOfDay(),
                        fechaFinal.atTime(LocalTime.MAX), idUsuarioFirmado)
                .stream().map(this::fromCuponera)
                .toList();

    }

    @Override
    public List<CuponDTO> cuponesByCategoriaAndEstado(Integer idCategoria, Integer idEstado) {
        List<CuponerappCategoriaEstadoEntity> list = cuponerappCategoriaEstadoEntityRepository.findByCategoriaIdCategoriaAndEstadoIdEstado(idCategoria, idEstado);
        return list.stream().map(CuponDTO::new).collect(Collectors.toList());
    }


    private CuponCanjeadoDTO fromCuponera(CuponerappUsadaEntity cuponerappUsadaEntity) {
        return new CuponCanjeadoDTO()
                .setCodigoQr(cuponerappUsadaEntity.getCodigoQr())
                .setFecha(cuponerappUsadaEntity.getMomento())
                .setFolio(cuponerappUsadaEntity.getFolio())
                .setIdPromocion(cuponerappUsadaEntity.getCuponUsado().getIdPromocion())
                .setImagenBase64(cuponerappUsadaEntity.getImagenBase64())
                .setNombrePromocion(cuponerappUsadaEntity.getCuponUsado().getPromocion())
                .setPuntosUsados(cuponerappUsadaEntity.getPuntos());

    }

    private int descuentaPuntosCanje(Long idUsuario, int puntosDescontar) {
        var usuarioPuntos = this.usuarioPuntosColorEntityRepository.findByIdArqUsuario(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usted no tiene punto acumulados."));
        if (usuarioPuntos.getPuntos() < puntosDescontar) {
            throw new IllegalArgumentException("Sus puntos no son suficientes para el canje.");
        }
        var puntosRestantes = usuarioPuntos.getPuntos() - puntosDescontar;
        usuarioPuntos.setPuntos(puntosRestantes);
        var usuarioPuntosColor = this.usuarioPuntosColorEntityRepository.save(usuarioPuntos);

        actualizaPuntosEventoProducer.send(usuarioPuntosColor);

        return puntosRestantes;
    }

    private void guardaHistorico(CuponerappEntity cuponUsado, Long idUsuario, RespuestaCuponeraDTO respuestaCuponeraDTO) {
        var ent = new CuponerappUsadaEntity()
                .setArqUsuarioId(idUsuario)
                .setCuponUsado(cuponUsado)
                .setPuntos(cuponUsado.getPuntos())
                .setMomento(LocalDateTime.now())
                .setCodigoQr(respuestaCuponeraDTO.getCodigoQr())
                .setFecha(respuestaCuponeraDTO.getFecha())
                .setHora(respuestaCuponeraDTO.getHora())
                .setImagenBase64(respuestaCuponeraDTO.getImagenBase64())
                .setFolio(respuestaCuponeraDTO.getFolio())
                .setCosto(respuestaCuponeraDTO.getCosto())
                .setNombre(respuestaCuponeraDTO.getNombre())
                .setFechaVigencia(cuponUsado.getFechaVigencia().toString())
                .setIdCompra(cuponUsado.getIdCompra());
        this.cuponerappUsadaEntityRepository.save(ent);
    }
}
