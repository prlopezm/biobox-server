package mx.com.tecnetia.orthogonal.utils.notificaciones;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.orthogonal.dto.notificaciones.FirebaseNotificationDTO;
import mx.com.tecnetia.orthogonal.dto.notificaciones.FirebaseNotificationResponseDTO;
import mx.com.tecnetia.orthogonal.persistence.hibernate.repository.ArqPropiedadEntityRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class FirebaseNotificationsService {
    private final ArqPropiedadEntityRepository arqPropiedadEntityRepository;
    private final Environment environment;

    //@Async("asyncExecutor")
    public void sendNotification(FirebaseNotificationDTO firebaseNotificationDTO) {
        String title = environment.getProperty("notification.firebase.title");
        String sound = environment.getProperty("notification.firebase.sound");
        String contentAvailable = environment.getProperty("notification.firebase.content_available");
        String priority = environment.getProperty("notification.firebase.priority");
        String urlEndpoint = environment.getProperty("notification.firebase.url");
        String key = environment.getProperty("notification.firebase.key");

        firebaseNotificationDTO.getNotification().setSound(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(sound)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + sound)).getValor());
        firebaseNotificationDTO.getNotification().setTitle(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(title)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + title)).getValor());
        String contentAvailableStr =  this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(contentAvailable)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + contentAvailable)).getValor();
        firebaseNotificationDTO.setContent_available(contentAvailableStr != null ? (contentAvailableStr.equals("") ? null : Integer.getInteger(contentAvailableStr)) : null);
        firebaseNotificationDTO.setPriority(this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(priority)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + priority)).getValor());

        HttpEntity<?> requestEntity = null;
        FirebaseNotificationResponseDTO responseEntity = null;
        var restTemplate = new RestTemplate();
        String keyStr =  this.arqPropiedadEntityRepository.findArqPropiedadEntitiesByActivoIsTrueAndCodigo(key)
                .orElseThrow(() -> new IllegalArgumentException("No existe en la BD: " + key)).getValor();
        boolean reintentar = false;
        try {
            var headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "key=" + keyStr);
            requestEntity = new HttpEntity<>(firebaseNotificationDTO, headers);
            responseEntity = restTemplate.postForObject(urlEndpoint, requestEntity, FirebaseNotificationResponseDTO.class);

            if (responseEntity == null) {
               reintentar = true;
            }else{
                if(responseEntity.getSuccess() != 1){
                    reintentar = true;
                }
            }
        }catch(RestClientException e){
                reintentar = true;
        }

        if(reintentar){
            try{
                responseEntity = restTemplate.postForObject(urlEndpoint, requestEntity, FirebaseNotificationResponseDTO.class);
            }catch(RestClientException e){
                log.error("Error al enviar la notificación: {}",e.getMessage());
            }
        }
    }
}
