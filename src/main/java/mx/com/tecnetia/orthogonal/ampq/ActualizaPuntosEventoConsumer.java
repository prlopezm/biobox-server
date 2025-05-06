package mx.com.tecnetia.orthogonal.ampq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mx.com.tecnetia.orthogonal.ampq.eventos.ActualizaPuntosEvento;
import mx.com.tecnetia.orthogonal.services.UsuarioPuntosColorService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActualizaPuntosEventoConsumer {

    @Autowired
    private UsuarioPuntosColorService usuarioPuntosColorService;


    @RabbitListener(queues = "${puntos.event.queue}")
    public void receive(String message) {
        try {
            log.info("Actualizando puntos usuario");
            ObjectMapper mapper = new ObjectMapper();
            var evento = mapper.readValue(message, ActualizaPuntosEvento.class);
            usuarioPuntosColorService.guardarPuntos(evento);
            log.info("Se actualizo los puntos");
        } catch (Exception ex) {
            log.error("Error al guardar evento actualizacion de puntos {} ", ex.getMessage());
        }
    }
}
