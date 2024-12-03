package mx.com.tecnetia.marcoproyectoseguridad.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.QuioscoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.ProductoAReciclarDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.ReciclajeTerminadoRequestDTO;

public interface ReciclajeService {

    ProductoAReciclarDTO getProductoAReciclar(String barCode,Long idUsuarioLogeado);
    void reciclaProductoEnQuioscoConPeso(Long idUsuario, Long idProducto, Long idQuiosco, Integer codigoRespuesta, Integer peso);
    void reciclaProductoEnQuioscoConFoto(@NotNull Long idUsuario, @NotNull Long idProducto, @NotNull Long idQuiosco,
                                         Integer codigoRespuesta, @NotBlank String foto, @NotBlank String barcode);
    MensajeDTO<?> reciclaProducto(Long idUsuarioLogeado, Long idProducto, byte[] foto);
    ProductoAReciclarDTO enviarProcesoDeReciclajeEnQuiosco(Long idUsuarioLogeado, String barCode, Long idQuiosco);    
    MensajeDTO<?> validaProducto(Long idProducto, byte[] foto);
    MensajeDTO<?> validaProductoCodigoBarras(Long idProducto, String foto);
    QuioscoDTO buscaMaquina(String qr, Long idUsuarioLogeado);
    void validaLlenadoMaquinas();
    void enviarNotificacionReciclajeTerminado(ReciclajeTerminadoRequestDTO reciclajeTerminadoRequestDTO);
    boolean usuarioPuedeReciclar(Long idUsuario);
    void estresarQuiosco(Long idQuiosco);
    Boolean quioscoEstaEnUso(Long idQuiosco);    
    
}
