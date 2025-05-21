package mx.com.tecnetia.orthogonal.services;

import lombok.AllArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.ColorEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorEntity;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.repository.UsuarioPuntosColorEntityRepository;
import mx.com.tecnetia.orthogonal.ampq.eventos.ActualizaPuntosEvento;
import mx.com.tecnetia.orthogonal.ampq.eventos.EventoReciclaje;
import mx.com.tecnetia.orthogonal.persistence.hibernate.entity.ArqUsuarioEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioPuntosColorServiceImpl implements UsuarioPuntosColorService {

    private UsuarioPuntosColorEntityRepository usuarioPuntosColorEntityRepository;

    @Override
    public void guardarPuntos(ActualizaPuntosEvento actualizaPuntosEvento) {
        var usuarioPuntosColorOpt = usuarioPuntosColorEntityRepository.findByIdArqUsuario(actualizaPuntosEvento.getUsuarioPuntosColorEntities().getIdArqUsuario());
        if (usuarioPuntosColorOpt.isPresent()) {
            var usuarioPuntos = usuarioPuntosColorOpt.get();
            usuarioPuntos.setPuntos(actualizaPuntosEvento.getUsuarioPuntosColorEntities().getPuntos());
            usuarioPuntos.setColorByIdColor(new ColorEntity(actualizaPuntosEvento.getUsuarioPuntosColorEntities().getIdColor()));
            usuarioPuntosColorEntityRepository.save(usuarioPuntos);
            return;
        }
        var usuarioPuntosColor = new UsuarioPuntosColorEntity();
        usuarioPuntosColor.setArqUsuarioByIdArqUsuario(new ArqUsuarioEntity(actualizaPuntosEvento.getUsuarioPuntosColorEntities().getIdArqUsuario()));
        usuarioPuntosColor.setPuntos(actualizaPuntosEvento.getUsuarioPuntosColorEntities().getPuntos());
        usuarioPuntosColor.setColorByIdColor(new ColorEntity(actualizaPuntosEvento.getUsuarioPuntosColorEntities().getIdColor()));
        usuarioPuntosColorEntityRepository.save(usuarioPuntosColor);
    }
}
