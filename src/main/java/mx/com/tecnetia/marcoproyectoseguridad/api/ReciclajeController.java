package mx.com.tecnetia.marcoproyectoseguridad.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.QuioscoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.*;
import mx.com.tecnetia.marcoproyectoseguridad.service.ReciclajeService;
import mx.com.tecnetia.orthogonal.dto.IllegalArgumentExceptionDTO;
import mx.com.tecnetia.orthogonal.dto.UnAuthorizedDTO;
import mx.com.tecnetia.orthogonal.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reciclaje")
@Validated
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Proceso de Reciclaje.", description = "Servicios para todo el proceso de reciclaje BioBox.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Fallo en el parámetro de entrada.",
                content = @Content(schema = @Schema(implementation = IllegalArgumentExceptionDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado.", content = @Content(schema = @Schema(implementation = UnAuthorizedDTO.class)))})
public class ReciclajeController {
    private final ReciclajeService reciclajeService;
    private final UsuarioService usuarioService;

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Identificar producto. LISTO", description = "ROLE_RECICLADOR. Identifica si existe un producto por medio de su codigo de barras",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = ProductoAReciclarDTO.class)))})
    @GetMapping(value = "/bar-code")
    public ResponseEntity<ProductoAReciclarDTO> identificarBarCode(@RequestParam("barCode") @NotEmpty @Parameter(description = "Codigo de barras del producto.") String barCode,
                                                                   @RequestParam(name = "idQuiosco") Long idQuiosco) {
        ProductoAReciclarDTO producto = null;
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        if (idQuiosco == 0) {
            producto = this.reciclajeService.getProductoAReciclar(barCode, idUsuarioLogueado);
        } else {
            producto = this.reciclajeService.enviarProcesoDeReciclajeEnQuiosco(idUsuarioLogueado, barCode, idQuiosco);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Quiosco en uso. LISTO", description = "ROLE_RECICLADOR. Verifica si un quiosco esta siendo utilizado aun",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = ResponseQuioscoEnUsoDTO.class)))})
    @GetMapping(value = "/quisco-en-uso")
    public ResponseEntity<ResponseQuioscoEnUsoDTO> quioscoEnUso(@RequestParam(name = "idQuiosco") Long idQuiosco) {
        ResponseQuioscoEnUsoDTO quioscoEnUsoDTO = new ResponseQuioscoEnUsoDTO();
        quioscoEnUsoDTO.setQuioscoEnUso(true);
        Boolean quioscoEnUso = this.reciclajeService.quioscoEstaEnUso(idQuiosco);
        quioscoEnUsoDTO.setQuioscoEnUso(quioscoEnUso);
        log.info("**** QUIOSCO ESTA EN USO Controller? {}", quioscoEnUso);
        return new ResponseEntity<>(quioscoEnUsoDTO, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Estresar máquina. LISTO", description = "ROLE_RECICLADOR. Estresa una máquina",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = ProductoAReciclarDTO.class)))})
    @GetMapping(value = "/estresar-maquina")
    public ResponseEntity<Void> identificarBarCode(@RequestParam(name = "idQuiosco") Long idQuiosco) {
        this.reciclajeService.estresarQuiosco(idQuiosco);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Buscar maquina. LISTO", description = "ROLE_RECICLADOR. Busca la maquina donde se encuentra el usuario y quiere reciclar",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = ProductoAReciclarDTO.class)))})
    @GetMapping(value = "/busca-maquina")
    public ResponseEntity<QuioscoDTO> buscarMaquina(@RequestParam("qr")
                                                    @NotEmpty
                                                    @Parameter(description = "QR de la maquina en la que se encuentra el usuario.")
                                                    String qr) {
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        QuioscoDTO maquina = this.reciclajeService.buscaMaquina(qr, idUsuarioLogueado);
        return new ResponseEntity<>(maquina, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_QUIOSCO')")
    @Operation(summary = "Valida el producto con algoritmo IA. LISTO.",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = MensajeDTO.class)))})
    @PostMapping(value = "/valida-producto")
    public ResponseEntity<MensajeDTO<?>> validaProducto(@Valid @RequestBody ReciclaProductoDTO reciclaProductoDTO) {
        byte[] fotoByte = Base64.getDecoder().decode(reciclaProductoDTO.getFoto());
        MensajeDTO<?> mensaje = this.reciclajeService.validaProducto(reciclaProductoDTO.getPrd(), fotoByte);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_QUIOSCO')")
    @Operation(summary = "Valida el producto en base a su codigo de barras de la foto. LISTO.",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = MensajeDTO.class)))})
    @PostMapping(value = "/valida-codigo-barras")
    public ResponseEntity<MensajeDTO<?>> validaProductoCodigoBarras(@Valid @RequestBody ReciclaProductoDTO reciclaProductoDTO) {
        MensajeDTO<?> mensaje = this.reciclajeService.validaProductoCodigoBarras(reciclaProductoDTO.getPrd(), reciclaProductoDTO.getFoto());
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Valida el producto con algoritmo IA y si es correcto lo recicla. LISTO.",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = MensajeDTO.class)))})
    @PostMapping(value = "/recicla-producto")
    public ResponseEntity<MensajeDTO<?>> reciclaProducto(@Valid @RequestBody ReciclaProductoDTO reciclaProductoDTO) {
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        byte[] fotoByte = Base64.getDecoder().decode(reciclaProductoDTO.getFoto());
        MensajeDTO<?> mensaje = this.reciclajeService.reciclaProducto(idUsuarioLogueado, reciclaProductoDTO.getPrd(), fotoByte);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_QUIOSCO')")
    @Operation(summary = "Recibe la respuesta del proceso de reciclaje, de una máquina. LISTO.",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = void.class)))})
    @PostMapping(value = "/recicla-producto-quiosco")
    public ResponseEntity<Void> reciclaProductoQuiosco(@Valid @RequestBody ReciclaProductoQuioscoResponseDTO reciclaProductoQuioscoDTO) {
        var foto = reciclaProductoQuioscoDTO.getFoto() != null ? reciclaProductoQuioscoDTO.getFoto().length() : null;
        log.info("End Point /recicla-producto-quiosco. BarCode:{}, idProducto: {}, respuesta: {}, peso: {}, tamaño foto: {}",
                reciclaProductoQuioscoDTO.getBarCode(), reciclaProductoQuioscoDTO.getIdProducto(), reciclaProductoQuioscoDTO.getCodigoRespuesta()
                , reciclaProductoQuioscoDTO.getPeso(), foto);
        if (reciclaProductoQuioscoDTO.getPeso() != null) {
            log.info("Se recicla desde un PLC");
            this.reciclajeService.reciclaProductoEnQuioscoConPeso(reciclaProductoQuioscoDTO.getIdUsuario(),
                    reciclaProductoQuioscoDTO.getIdProducto(),
                    reciclaProductoQuioscoDTO.getIdQuiosco(),
                    reciclaProductoQuioscoDTO.getCodigoRespuesta(),
                    reciclaProductoQuioscoDTO.getPeso());
        } else if (reciclaProductoQuioscoDTO.getFoto() != null) {
            log.info("Se recicla desde un Arduino");
            this.reciclajeService.reciclaProductoEnQuioscoConFoto(reciclaProductoQuioscoDTO.getIdUsuario(),
                    reciclaProductoQuioscoDTO.getIdProducto(),
                    reciclaProductoQuioscoDTO.getIdQuiosco(),
                    reciclaProductoQuioscoDTO.getCodigoRespuesta(),
                    reciclaProductoQuioscoDTO.getFoto(),
                    reciclaProductoQuioscoDTO.getBarCode());
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_QUIOSCO')")
    @Operation(summary = "Realiza el aviso al usuario de que ya termino el reciclaje. LISTO.",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = void.class)))})
    @PostMapping(value = "/notificacion-reciclaje-terminado")
    public ResponseEntity<Void> enviaNotificacionReciclajeTerminado(@Valid @RequestBody ReciclajeTerminadoRequestDTO reciclajeTerminadoRequestDTO) {
        this.reciclajeService.enviarNotificacionReciclajeTerminado(reciclajeTerminadoRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Validar nivel de depósito de máquinas y enviar mail. LISTO", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = void.class)))})
    @GetMapping("/llenado-maquinas")
    public ResponseEntity<Void> validarLlenadoMaquinas() {
        this.reciclajeService.validaLlenadoMaquinas();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_QUIOSCO')")
    @Operation(summary = "Recibe la respuesta del proceso de reciclaje, de una máquina. LISTO.",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = void.class)))})
    @PostMapping(value = "/v2/recicla-producto-quiosco")
    public ResponseEntity<Void> reciclaProductoQuioscoV2(@Valid @RequestBody ReciclaProductoQuioscoResponseV2DTO reciclaProductoQuioscoDTO) {
        log.info("End Point /v2/recicla-producto-quiosco. BarCode:{}, idProducto: {}, respuesta: {}, peso: {}",
                reciclaProductoQuioscoDTO.getBarCode(), reciclaProductoQuioscoDTO.getIdsProducto(), reciclaProductoQuioscoDTO.getCodigoRespuesta()
                , reciclaProductoQuioscoDTO.getPeso());
        if (reciclaProductoQuioscoDTO.getPeso() != null) {
            log.info("Se recicla desde un PLC");
            this.reciclajeService.reciclaProductoEnQuioscoPlc(reciclaProductoQuioscoDTO.getIdUsuario(),
                    reciclaProductoQuioscoDTO.getIdsProducto(),
                    reciclaProductoQuioscoDTO.getIdQuiosco(),
                    reciclaProductoQuioscoDTO.getCodigoRespuesta(),
                    reciclaProductoQuioscoDTO.getPeso());
        } else {
            log.info("Se recicla desde un Arduino");
            this.reciclajeService.reciclaProductoEnQuioscoArduino(reciclaProductoQuioscoDTO.getIdUsuario(),
                    reciclaProductoQuioscoDTO.getIdsProducto(),
                    reciclaProductoQuioscoDTO.getIdQuiosco(),
                    reciclaProductoQuioscoDTO.getCodigoRespuesta(),
                    reciclaProductoQuioscoDTO.getBarCode());
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Identificar producto. LISTO", description = "ROLE_RECICLADOR. Identifica si existe un producto por medio de su codigo de barras",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = ProductoAReciclarDTO.class)))})
    @GetMapping(value = "/v2/bar-code")
    public ResponseEntity<List<ProductoAReciclarDTO>> identificarBarCodeV2(@RequestParam("barCode") @NotEmpty @Parameter(description = "Codigo de barras del producto.") String barCodes,
                                                                           @RequestParam(name = "idQuiosco") Long idQuiosco) {
        List<ProductoAReciclarDTO> productos = null;
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        if (idQuiosco == 0) {
            productos = this.reciclajeService.getListaProductosAReciclar(barCodes, idUsuarioLogueado);
        } else {
            productos = this.reciclajeService.enviarProcesosDeReciclajeEnQuiosco(idUsuarioLogueado, barCodes, idQuiosco);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Cerrar puerta quiosco.", description = "ROLE_RECICLADOR. Cierra puerta quiosco",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = void.class)))})
    @PostMapping(value = "/cerrar-quiosco")
    public ResponseEntity<Void> cerrarQuiosco(@Valid @RequestBody CerrarQuioscoDTO cerrarQuioscoDTO) {
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        log.info("Cierra quiosco. Quiosco id {}", cerrarQuioscoDTO.getQuioscoId());
        this.reciclajeService.cerrarQuiosco(idUsuarioLogueado, cerrarQuioscoDTO.getQuioscoId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Abrir puerta quiosco.", description = "ROLE_RECICLADOR. Abre puerta quiosco",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = void.class)))})
    @PostMapping(value = "/abrir-quiosco")
    public ResponseEntity<Void> abrirQuiosco(@Valid @RequestBody AbrirQuioscoDTO abrirQuioscoDTO) {
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        log.info("Abre el quiosco. Quiosco id {}", abrirQuioscoDTO.getQuioscoId());
        this.reciclajeService.abrirQuiosco(abrirQuioscoDTO.getQuioscoId(), idUsuarioLogueado);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
