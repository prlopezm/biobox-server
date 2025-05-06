package mx.com.tecnetia.marcoproyectoseguridad.api;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.MovimientosListDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.ProgramaCategoriaListDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.programalealtad.PuntosUsuarioColorListDTO;
import mx.com.tecnetia.marcoproyectoseguridad.service.BacardiService;
import mx.com.tecnetia.marcoproyectoseguridad.service.PranaService;
import mx.com.tecnetia.marcoproyectoseguridad.service.ProgramaLealtadService;
import mx.com.tecnetia.marcoproyectoseguridad.service.ProntiPagoService;
import mx.com.tecnetia.marcoproyectoseguridad.util.TipoProgramaEnum;
import mx.com.tecnetia.orthogonal.dto.IllegalArgumentExceptionDTO;
import mx.com.tecnetia.orthogonal.dto.UnAuthorizedDTO;
import mx.com.tecnetia.orthogonal.services.UsuarioService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/programa-lealtad")
@Validated
@RequiredArgsConstructor
@Tag(name = "Programas de Lealtad.", description = "Programas de Lealtad de BioBox.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Fallo en el parámetro de entrada.",
                content = @Content(schema = @Schema(implementation = IllegalArgumentExceptionDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado.", content = @Content(schema = @Schema(implementation = UnAuthorizedDTO.class)))})
public class ProgramasLealtadRestController {
	
    private final ProgramaLealtadService programaLealtadService;    
    private final UsuarioService usuarioService;
	private final ProntiPagoService prontiPagoService;
	private final PranaService pranaService;
	private final BacardiService bacardiService;

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Puntos saldos y movimientos de un usuario. LISTO", description = "ROLE_RECICLADOR. Obtiene el resumen y detalle de los ultimos saldos y movimientos de un usuario",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = MovimientosListDTO.class)))})
    @GetMapping(value = "/saldos-movimientos-usuario")
    public ResponseEntity<MovimientosListDTO> getSaldosYMovimientosUsuario() {
        var usuarioLogeado = this.usuarioService.getUsuarioLogeado();
        var puntosUsuario = this.programaLealtadService.getPuntosUsuarioXColor(usuarioLogeado.getIdArqUsuario());
        var movimientos = this.programaLealtadService.getUltimosMovimientosDeUsuario(usuarioLogeado.getIdArqUsuario());

        MovimientosListDTO movimientosListDTO = new MovimientosListDTO();
        movimientosListDTO.setNombre(usuarioLogeado.getNombres());
        movimientosListDTO.setPaterno(usuarioLogeado.getApellidoPaterno());
        movimientosListDTO.setMaterno(usuarioLogeado.getApellidoMaterno());
        movimientosListDTO.setNick(usuarioLogeado.getEmail());
        movimientosListDTO.setPuntosActuales(puntosUsuario);
        movimientosListDTO.setUltimosMovimientos(movimientos);

        return new ResponseEntity<>(movimientosListDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Puntos del usuario por color. LISTO", description = "ROLE_RECICLADOR. Obtiene los puntos de un usuario por cada color",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = PuntosUsuarioColorListDTO.class)))})
    @GetMapping(value = "/puntos-usuario")
    public ResponseEntity<PuntosUsuarioColorListDTO> getPuntosUsuario() {
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        var ret = this.programaLealtadService.getPuntosUsuarioXColor(idUsuarioLogueado);
        PuntosUsuarioColorListDTO puntosList = new PuntosUsuarioColorListDTO();
        puntosList.setPuntos(ret);
        return new ResponseEntity<>(puntosList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Puntos de programas de lealtad por color. LISTO", description = "ROLE_RECICLADOR. Obtiene los puntos de los programas de lealtad por cada color",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = ProgramaCategoriaListDTO.class)))})
    @GetMapping(value = "/puntos-programas-lealtad")
    public ResponseEntity<ProgramaCategoriaListDTO> getPuntosProgramasLealtad() {
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        ProgramaCategoriaListDTO puntosList = new ProgramaCategoriaListDTO();

        if(idUsuarioLogueado==14) {
            var ret = this.programaLealtadService.getProgramasLealtadFake();
            puntosList.setProgramasLealtad(ret);
        }else {
            var ret = this.programaLealtadService.getProgramasLealtad(idUsuarioLogueado);
            puntosList.setProgramasLealtad(ret);
        }
        return new ResponseEntity<>(puntosList, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Canjear Programa. LISTO", description = "ROLE_RECICLADOR. Realiza el canje de un programa de lealtad y realiza el descuento de puntos correspondientes del cliente",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = MensajeDTO.class)))})
    @GetMapping(value = "/canjear-programa")
    public ResponseEntity<MensajeDTO<?>> canjearPrograma(@RequestParam("sku") @NotEmpty @Parameter(description = "SKU del programa a canjear.") String sku, 
																		    		   @RequestParam(name = "monto") BigDecimal monto,
																		    		   @RequestParam(name = "tipoPrograma") String tipoPrograma,
																		    		   @RequestParam(name = "idPuntosRequeridos") Long idPuntosRequeridos) {
    	var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        MensajeDTO<?> mensaje = null;
    	
		if (tipoPrograma.equals(TipoProgramaEnum.PRONTIPAGO.getTipoPrograma())) {
			mensaje  = this.prontiPagoService.canjearProntipago(sku, monto, idUsuarioLogueado);
		}else if (tipoPrograma.equals(TipoProgramaEnum.PRANA.getTipoPrograma())) {
			mensaje  = this.pranaService.canjearCodigoDescuento(sku, idPuntosRequeridos, idUsuarioLogueado);
		}else if (tipoPrograma.equals(TipoProgramaEnum.BACARDI.getTipoPrograma())) {
			mensaje = this.bacardiService.canjearCodigoDescuento(sku, idPuntosRequeridos, idUsuarioLogueado);
		}

        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "Canjear Programa Lealtad. LISTO", description = "ROLE_RECICLADOR. Realiza el canje del programa de lealtad y realiza el descuento de puntos correspondientes del cliente",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = MensajeDTO.class)))})
    @GetMapping(value = "/canjear")
    public ResponseEntity<MensajeDTO<?>> canjearPuntos(@RequestParam("sku") @NotEmpty @Parameter(description = "SKU del programa a canjear.") String sku,
                                                       @RequestParam(name = "monto") BigDecimal monto,
                                                       @RequestParam(name = "idPuntosRequeridos") Long idPuntosRequeridos) {
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        MensajeDTO<?> mensaje = this.bacardiService.canjearCodigoDescuento(sku, idPuntosRequeridos, idUsuarioLogueado);

        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }
    
}
