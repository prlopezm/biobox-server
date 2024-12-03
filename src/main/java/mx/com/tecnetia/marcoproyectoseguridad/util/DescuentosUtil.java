package mx.com.tecnetia.marcoproyectoseguridad.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class DescuentosUtil {

	public static String formatoValor(Long idTipoDescuento, BigDecimal valorN, BigDecimal valorX) {
		NumberFormat formatoValorDescuento = null;
		String descuento = "";
		
		if (idTipoDescuento == TipoDescuentoEnum.PORCENTAJE.getTipoDescuento()) {
			formatoValorDescuento = NumberFormat.getPercentInstance();
			descuento = formatoValorDescuento.format(valorN.divide(new BigDecimal(100)));
		} else if(idTipoDescuento == TipoDescuentoEnum.MONTO.getTipoDescuento()) {
			formatoValorDescuento = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
			descuento = formatoValorDescuento.format(valorN);
		} else if(idTipoDescuento == TipoDescuentoEnum.PRODUCTO_GRATIS.getTipoDescuento()){
			descuento = valorN.toString();
		} else if(idTipoDescuento == TipoDescuentoEnum.N_POR_X_PRECIO.getTipoDescuento()) {
			descuento = valorN.toString() + "x" + valorX.toString();
		} else {
			formatoValorDescuento = NumberFormat.getNumberInstance();
			descuento = formatoValorDescuento.format(valorN);
		}
		return descuento;
	}
	
}
