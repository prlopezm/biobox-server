package mx.com.tecnetia.orthogonal.ampq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import mx.com.tecnetia.marcoproyectoseguridad.persistence.hibernate.entity.UsuarioPuntosColorEntity;
import mx.com.tecnetia.orthogonal.ampq.dto.UsuarioPuntosColorDTO;
import mx.com.tecnetia.orthogonal.ampq.eventos.ActualizaPuntosEvento;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActualizaPuntosEventoProducer {

    @Autowired
    private RabbitTemplate template;

    @Value("${puntos.event.exchange.name}")
    private String exchange;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send(UsuarioPuntosColorEntity usuarioPuntosColorEntity) {
        try {
            var usuarioPuntosColor = new UsuarioPuntosColorDTO();
            usuarioPuntosColor.setPuntos(usuarioPuntosColorEntity.getPuntos());
            usuarioPuntosColor.setIdArqUsuario(usuarioPuntosColorEntity.getIdArqUsuario());
            usuarioPuntosColor.setIdColor(usuarioPuntosColorEntity.getIdColor());

            var actualizaPuntosEvento = new ActualizaPuntosEvento(usuarioPuntosColor);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            this.template.convertAndSend(exchange, "", mapper.writeValueAsString(actualizaPuntosEvento));
            log.info("Enviando mensaje reciclaje");
        } catch (JsonProcessingException e) {
            log.error("Error envio evento descuento puntos {}",  e.getMessage());
        }
    }
}
