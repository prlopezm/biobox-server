package mx.com.tecnetia.orthogonal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class MailDTO {
//    private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String mailSubject;
    private String mailContent;
    private String contentType;
    private List<Object> attachments;
    private Map<String, Object> model;

    public MailDTO() {
        contentType = "text/html";
    }
}
