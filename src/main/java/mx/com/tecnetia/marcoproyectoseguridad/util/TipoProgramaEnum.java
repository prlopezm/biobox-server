package mx.com.tecnetia.marcoproyectoseguridad.util;

public enum TipoProgramaEnum {
    
	METRO("m"),
    OXXO("o"),
    PRONTIPAGO("p"),
	BACARDI("b"),
	PRANA("r");
	
    private String tipoPrograma;

    TipoProgramaEnum(String tipoPrograma){
        this.tipoPrograma = tipoPrograma;
    }
    public String getTipoPrograma() {
        return this.tipoPrograma;
    }

    public void setTipoPrograma(String tipoPrograma) {
        this.tipoPrograma = tipoPrograma;
    }


}
