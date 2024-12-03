package mx.com.tecnetia.orthogonal.dto.notificaciones;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de una notificación en firebase.")
public class FirebaseNotificationDTO {
    private NotificationDTO notification;
    private DataDTO data;
    @JsonProperty("content-available")
    private Integer content_available;
    private String to;
    private String priority;
}
