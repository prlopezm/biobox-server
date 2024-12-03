package mx.com.tecnetia.marcoproyectoseguridad.util;

public enum CodigoRespuestaMaquinaEnum {
    FOTO_INVALIDA(1),
    FALLO_RECICLAJE(2),
    RECICLAJE_EXITOSO(0);
    private int codigoRespuesta;

    CodigoRespuestaMaquinaEnum(int codigoRespuesta){
        this.codigoRespuesta = codigoRespuesta;
    }
    public int getCodigoRespuesta() {
        return this.codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }


}
