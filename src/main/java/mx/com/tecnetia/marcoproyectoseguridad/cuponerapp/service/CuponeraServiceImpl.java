package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponCanjeadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.RespuestaCuponCanjeadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.RespuestaCuponeraDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappEntity;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappUsadaEntity;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.CuponerappEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.CuponerappUsadaEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.orthogonal.ampq.ActualizaPuntosEventoProducer;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    private final ActualizaPuntosEventoProducer actualizaPuntosEventoProducer;

    @Override
    @Transactional(readOnly = true)
    public List<CuponDTO> getCupones() {
        return this.cuponerappEntityRepository.getCupones();
    }

    @Override
    @Transactional
    public RespuestaCuponCanjeadoDTO canjear(RespuestaCuponeraDTO respuestaCuponeraDTO) {
        var cupon = this.cuponerappEntityRepository.findByIdPromocion(respuestaCuponeraDTO.getPromoId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el cupón especificado."));
        var idUsuarioFirmado = this.usuarioService.getUsuarioLogeado()
                .getIdArqUsuario();
        var puntosRestantes = descuentaPuntosCanje(idUsuarioFirmado, cupon.getPuntos());
        guardaHistorico(cupon, idUsuarioFirmado, respuestaCuponeraDTO);
        var msg = String.format("Se descontaron %1$d puntos. Te quedan %2$d puntos restantes", cupon.getPuntos(), puntosRestantes);
        return new RespuestaCuponCanjeadoDTO(msg, puntosRestantes);
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
                .setNombre(respuestaCuponeraDTO.getNombre());
        this.cuponerappUsadaEntityRepository.save(ent);
    }
}
