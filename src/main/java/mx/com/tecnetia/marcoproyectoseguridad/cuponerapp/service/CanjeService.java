package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.HistoricoCanjeDTO;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-31 11:36 AM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class CanjeService {
    private final UsuarioService usuarioService;
    private final HistoricoService historicoService;


    public List<HistoricoCanjeDTO> usadasPorUsuarioFirmado() {
        var idUsuarioFirmado = this.usuarioService.getUsuarioLogeado()
                .getIdArqUsuario();
        var cupones = this.historicoService.historicoCupones(idUsuarioFirmado);
        var oxxo = this.historicoService.historicoOxxo(idUsuarioFirmado);
        var recargas = this.historicoService.historicoRecargasCel(idUsuarioFirmado);
        CompletableFuture.allOf(cupones, oxxo, recargas).join();
        var ret = new ArrayList<HistoricoCanjeDTO>();
        ret.addAll(oxxo.join());
        ret.addAll(recargas.join());
        ret.addAll(cupones.join());
        return ret;
    }
}
