package mx.com.tecnetia.marcoproyectoseguridad.service;

import java.math.BigDecimal;
import java.util.List;

import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.client.ResponseCatalogProductTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.client.TransactionResponseDto;
import mx.com.tecnetia.marcoproyectoseguridad.dto.client.TransactionResponseTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.ProgramaCategoriaDTO;

public interface ProntiPagoService {
        
    TransactionResponseTO getEstatusProntipago(String transactionId);
    TransactionResponseDto enviarCanjeProntipago(Double amount, String clientReference, String sku);
    MensajeDTO<?> canjearProntipago(String sku, BigDecimal amount, Long idUsuarioLogeado);    
    List<ProgramaCategoriaDTO> getProgramasProntipagos(Long idArqUsuario, BigDecimal precioMax);
    ResponseCatalogProductTO getProgramasProntipagoWS();
}
