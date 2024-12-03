package mx.com.tecnetia.marcoproyectoseguridad.util;

public enum TipoDescuentoEnum {
	
    PORCENTAJE(1L),
    MONTO(2L),
    PRODUCTO_GRATIS(3L),
    N_POR_X_PRECIO(4L);
	
    private Long tipoDescuento;

    TipoDescuentoEnum(Long tipoDescuento){
        this.tipoDescuento = tipoDescuento;
    }
    public Long getTipoDescuento() {
        return this.tipoDescuento;
    }

    public void setTipoDescuento(Long tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

}
