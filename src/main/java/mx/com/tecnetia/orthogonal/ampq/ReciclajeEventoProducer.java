package mx.com.tecnetia.orthogonal.ampq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import mx.com.tecnetia.orthogonal.ampq.eventos.EventoReciclaje;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Slf4j
public class ReciclajeEventoProducer {

    @Autowired
    private RabbitTemplate template;

    @Value("${reciclaje.event.exchange.name}")
    private String exchange;

    @Value("${reciclaje.event.routing.key}")
    private String routingKey;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send(EventoReciclaje eventoReciclaje) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            this.template.convertAndSend(exchange, routingKey, mapper.writeValueAsString(eventoReciclaje));
            log.info("Enviando mensaje reciclaje");
        } catch (JsonProcessingException e) {
            log.error("Error envio evento reciclaje {}",  e.getMessage());
        }
    }
}
