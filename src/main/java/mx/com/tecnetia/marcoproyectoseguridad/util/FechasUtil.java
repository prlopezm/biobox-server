package mx.com.tecnetia.marcoproyectoseguridad.util;

import java.sql.Timestamp;
import java.time.Instant;

public class FechasUtil {

	public static boolean validaVigencia(Timestamp vigenciaInicio, Timestamp vigenciaFin)
	{	
		boolean vigente = false;
		Timestamp fechaActual = Timestamp.from(Instant.now());
    	if (vigenciaInicio == null && vigenciaFin == null) {
    		vigente = true;
    	} else if (vigenciaInicio == null && vigenciaFin != null && fechaActual.before(vigenciaFin)) {
    		vigente = true;	
    	} else if (vigenciaInicio == null && vigenciaFin != null && fechaActual.after(vigenciaFin)) {
    		vigente = false;	
    	} else if (vigenciaInicio != null && vigenciaFin == null && fechaActual.after(vigenciaInicio) ) {
    		vigente = true;
    	} else if(fechaActual.after(vigenciaInicio) && fechaActual.before(vigenciaFin)){
    		vigente = true;
    	}else {
    		vigente = false;
    	}
    	return vigente;
	}
}
