package mx.com.tecnetia.marcoproyectoseguridad.recargas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.RespuestaCuponCanjeadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.dto.DenominacionRecargaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.dto.RecargaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.entity.DenominacionRecargaCelEntity;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.entity.DenominacionRecargaCelUsadaEntity;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.repository.DenominacionRecargaCelEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.repository.DenominacionRecargaCelUsadaEntityRepository;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-23
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class RecargaServiceImpl implements RecargaService {
    private final DenominacionRecargaCelEntityRepository denominacionRecargaCelEntityRepository;
    private final UsuarioService usuarioService;
    private final UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;
    private final DenominacionRecargaCelUsadaEntityRepository denominacionRecargaCelUsadaEntityRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DenominacionRecargaDTO> getAllDenominacionRecargaCel() {
        return this.denominacionRecargaCelEntityRepository.getAllDenominacionRecargaCel();
    }

    @Override
    @Transactional
    public RespuestaCuponCanjeadoDTO registrarRecarga(RecargaDTO recarga) {
        var denominacionRecarga = this.denominacionRecargaCelEntityRepository.findById(recarga.getIdDenominacionRecargaDTO())
                .orElseThrow(() -> new IllegalArgumentException("Denominacion recarga no encontrada"));
        var idUsuarioFirmado = this.usuarioService.getUsuarioLogeado()
                .getIdArqUsuario();
        var puntosRestantes = descuentaPuntosCanje(idUsuarioFirmado, denominacionRecarga.getPuntos());
        guardaHistorico(denominacionRecarga, idUsuarioFirmado, recarga.getCel());
        var msg = String.format("Se descontaron %1$d puntos. Te quedan %2$d puntos restantes", denominacionRecarga.getPuntos(), puntosRestantes);
        return new RespuestaCuponCanjeadoDTO(msg, puntosRestantes);
    }

    private void guardaHistorico(DenominacionRecargaCelEntity denominacionRecarga, Long idUsuarioFirmado, String cel) {
        var ent = new DenominacionRecargaCelUsadaEntity()
                .setArqUsuarioId(idUsuarioFirmado)
                .setCel(cel)
                .setDenominacionRecargaCel(denominacionRecarga)
                .setMomento(LocalDateTime.now())
                .setPuntos(denominacionRecarga.getPuntos());
        this.denominacionRecargaCelUsadaEntityRepository.save(ent);
    }

    private int descuentaPuntosCanje(Long idUsuario, int puntosDescontar) {
        var usuarioPuntos = this.usuarioPuntosColorEntityRepository.findByIdArqUsuario(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usted no tiene punto acumulados."));
        if (usuarioPuntos.getPuntos() < puntosDescontar) {
            throw new IllegalArgumentException("Sus puntos no son suficientes para el canje.");
        }
        var puntosRestantes = usuarioPuntos.getPuntos() - puntosDescontar;
        usuarioPuntos.setPuntos(puntosRestantes);
        this.usuarioPuntosColorEntityRepository.save(usuarioPuntos);
        return puntosRestantes;
    }
}
