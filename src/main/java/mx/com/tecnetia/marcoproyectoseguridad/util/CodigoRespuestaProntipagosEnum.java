package mx.com.tecnetia.marcoproyectoseguridad.util;

public enum CodigoRespuestaProntipagosEnum {
    TRANSACCION_EXITOSA("00"),
    PROCESANDO_TRANSACCION("11"),
    NO_APLICA("N/A");
    private String codigoRespuesta;

    CodigoRespuestaProntipagosEnum(String codigoRespuesta){
        this.codigoRespuesta = codigoRespuesta;
    }
    public String getCodigoRespuesta() {
        return this.codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


}
