package mx.com.tecnetia.orthogonal.dto.notificaciones;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de una notificación en firebase.")
public class FirebaseNotificationResponseDTO implements Serializable {
	
    private static final long serialVersionUID = -724354322008772681L;
	private BigDecimal multicast_id;
    private Integer success;
    private Integer failure;
    private Integer canonical_ids;
    private ResultsResponseDTO[] results;

    public BigDecimal getMulticast_id() {
        return multicast_id;
    }

    public Integer getSuccess() {
        return success;
    }

    public Integer getFailure() {
        return failure;
    }

    public Integer getCanonical_ids() {
        return canonical_ids;
    }

    public ResultsResponseDTO[] getResults() {
        return results;
    }

    public void setMulticast_id(BigDecimal multicast_id) {
        this.multicast_id = multicast_id;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public void setFailure(Integer failure) {
        this.failure = failure;
    }

    public void setCanonical_ids(Integer canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public void setResults(ResultsResponseDTO[] results) {
        this.results = results;
    }
}
