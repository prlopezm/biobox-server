package mx.com.tecnetia.orthogonal.utils;

public enum EstatusVerificacionEnum {
	
    VERIFICADO("verificado"),
    NO_VERIFICADO("no-verificado");

    private String value;

    EstatusVerificacionEnum(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
