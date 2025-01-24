package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponCanjeadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappEntity;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.entity.CuponerappUsadaEntity;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.CuponerappEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.CuponerappUsadaEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Override
    @Transactional(readOnly = true)
    public List<CuponDTO> getCupones() {
        return this.cuponerappEntityRepository.getCupones();
    }

    @Override
    @Transactional
    public CuponCanjeadoDTO canjear(Integer promoId) {
        var cupon = this.cuponerappEntityRepository.findByIdPromocion(promoId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el cupón especificado."));
        var idUsuarioFirmado = this.usuarioService.getUsuarioLogeado()
                .getIdArqUsuario();
        var puntosRestantes = descuentaPuntosCanje(idUsuarioFirmado, cupon.getPuntos());
        guardaHistorico(cupon, idUsuarioFirmado);
        var msg = String.format("Se descontaron %1$d puntos. Te quedan %2$d puntos restantes", cupon.getPuntos(), puntosRestantes);
        return new CuponCanjeadoDTO(msg, puntosRestantes);
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

    private void guardaHistorico(CuponerappEntity cuponUsado, Long idUsuario) {
        var ent = new CuponerappUsadaEntity()
                .setArqUsuarioId(idUsuario)
                .setCuponUsado(cuponUsado)
                .setPuntos(cuponUsado.getPuntos())
                .setMomento(LocalDateTime.now());
        this.cuponerappUsadaEntityRepository.save(ent);
    }
}
