package mx.com.tecnetia.orthogonal.components;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.orthogonal.beans.ServicioRestDTOBean;

@Component
@RequiredArgsConstructor
public class ServicioRestDataComponentImpl implements ServicioRestDataComponent {
    private final ServicioRestDTOBean servicioRestDTOBean;
    @Value("${server.servlet.context-path}")
    private String appContext;
    @Value("${arq_modulo.codigo}")
    private String arqModuloCodigo;
    @Value("${arq_aplicacion.codigo}")
    private String arqAplicacionCodigo;

    @Override
    public boolean isActive(String uri, String method) {
        var lista = servicioRestDTOBean.getListaServiciosActivos().stream()
                .filter(val->StringUtils.contains(val.getUri(), uri) && val.getMetodo().equals(method)).toList();
        return !lista.isEmpty();
    }
}
