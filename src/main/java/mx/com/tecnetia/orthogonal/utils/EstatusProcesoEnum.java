package mx.com.tecnetia.orthogonal.utils;

public enum EstatusProcesoEnum {
	
    EXITOSO("true"),
    FALLIDO("false");

    private String value;

    EstatusProcesoEnum(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
