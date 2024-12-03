package mx.com.tecnetia.orthogonal.utils;

public enum EmailPropertiesEnum {
    HOST("email.host"),
    PASSW("email.password"),
    USER_NAME("email.username"),
    PORT("email.port"),
    SMTP_AUTH("mail.smtp.auth"),
    TRANSPORT_PROTOCOL("mail.transport.protocol"),
    DEBUG("mail.debug"),
    STARTTLS_ENABLE("mail.smtp.starttls.enable"),
    SEND_MAIL_GROUP("SEND_MAIL");

    private String codigo;

    EmailPropertiesEnum(String codigo){
        this.codigo = codigo;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


}
