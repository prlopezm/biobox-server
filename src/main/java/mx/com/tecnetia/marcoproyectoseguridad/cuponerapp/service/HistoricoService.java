package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.HistoricoCanjeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.persistence.repository.CuponerappUsadaEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository.CanjeOxxoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.persistence.repository.DenominacionRecargaCelUsadaEntityRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-31 12:16 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class HistoricoService {
    private final CanjeOxxoEntityRepository canjeOxxoEntityRepository;
    private final CuponerappUsadaEntityRepository cuponerappUsadaEntityRepository;
    private final DenominacionRecargaCelUsadaEntityRepository denominacionRecargaCelUsadaEntityRepository;

    @Async
    public CompletableFuture<List<HistoricoCanjeDTO>> historicoOxxo(Long idArqUsuario) {
        return CompletableFuture.completedFuture(this.canjeOxxoEntityRepository.usadasPor(idArqUsuario)
                .stream()
                .map(u -> new HistoricoCanjeDTO()
                        .setPuntosUsados(u.getOpcionCanjeOxxo().getPuntosCanjear())
                        .setNombrePromocion(u.getOpcionCanjeOxxo().getNombre())
                        .setFecha(u.getMomento())
                        .setFolio("Canje enviado por email.")
                        .setFecha(u.getMomento())
                        .setLlevaDetalle(false)
                        .setTipo("SPIN"))
                .toList());
    }

    @Async
    public CompletableFuture<List<HistoricoCanjeDTO>> historicoCupones(Long idArqUsuario) {
        return CompletableFuture.completedFuture(this.cuponerappUsadaEntityRepository.usadasPor(idArqUsuario)
                .stream()
                .map(u -> new HistoricoCanjeDTO()
                        .setNombrePromocion(u.getCuponUsado().getPromocion())
                        .setPuntosUsados(u.getPuntos())
                        .setFecha(u.getMomento())
                        .setFolio(u.getFolio())
                        .setCodigoQr(u.getCodigoQr())
                        .setImagenBase64(u.getImagenBase64())
                        .setLlevaDetalle(true)
                        .setTipo("CUPON")
                        .setIdCompra(u.getIdCompra()))
                .toList());
    }

    @Async
    public CompletableFuture<List<HistoricoCanjeDTO>> historicoRecargasCel(Long idArqUsuario) {
        return CompletableFuture.completedFuture(this.denominacionRecargaCelUsadaEntityRepository.usadasPor(idArqUsuario)
                .stream()
                .map(u -> new HistoricoCanjeDTO()
                        .setNombrePromocion(u.getDenominacionRecargaCel().getCompaniaCel().getNombre())
                        .setPuntosUsados(u.getPuntos())
                        .setFecha(u.getMomento())
                        .setLlevaDetalle(false)
                        .setTipo("RECARGA"))
                .toList());
    }
}
