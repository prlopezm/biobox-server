package mx.com.tecnetia.marcoproyectoseguridad.oxxo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.MenuOxxoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.entity.OxxoMemberIdEntity;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository.CanjeOxxoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository.OpcionCanjeOxxoEntityRepository;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.persistence.repository.OxxoMemberIdEntityRepository;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import mx.com.tecnetia.orthogonal.utils.propiedades.PropiedadComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final OxxoMemberIdEntityRepository oxxoMemberIdEntityRepository;
    private final OxxoCommonsService oxxoCommonsService;

    @Value("${OXXO_LEYENDA}")
    private String leyendaPropiedad;

    @Override
    @Transactional
    public MenuOxxoDTO obtenerMenu() {
        var usuarioLogeado = this.usuarioService.getUsuarioLogeado();
        var usuarioValido = oxxoCommonsService.validarUsuario(usuarioLogeado.getTelefono());
        if (Objects.equals(usuarioValido.getStatus(), "success")) {
            var opciones = this.opcionCanjeOxxoEntityRepository.getAllDTO();
            var menu = new MenuOxxoDTO()
                    .setLeyenda(leyendaOxxo())
                    .setPuntosRestantes(oxxoCommonsService.cantidadCanjesRestantes(usuarioLogeado.getIdArqUsuario()))
                    .setOpciones(opciones);
            this.actualizaMemberId(usuarioLogeado.getIdArqUsuario(), usuarioValido.getData().getMember_id());
            return menu;
        } else {
            throw new IllegalStateException("Para acceder a los beneficios Spin, por favor regístrate https://spinpremia.com/\"");
        }
    }

    private void actualizaMemberId(Long usuarioLogueadoId, String memberId) {
        var ent = this.oxxoMemberIdEntityRepository.findByArqUsuarioId(usuarioLogueadoId)
                .orElse(new OxxoMemberIdEntity()
                        .setArqUsuarioId(usuarioLogueadoId)
                        .setMemberId(memberId));
        this.oxxoMemberIdEntityRepository.save(ent);
    }

    /*
     * Devuelve la cantidad de canjes que le quedan al usuario logeado en el mes en curso
     * */


    private String leyendaOxxo() {
        return this.propiedadComponent.getPropiedades()
                .stream()
                .filter(v -> Objects.equals(v.getCodigo(), (this.leyendaPropiedad)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("BD mal configurada. Falta la leyenda de Oxxo."))
                .getValor();
    }

}
