package mx.com.tecnetia.marcoproyectoseguridad.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.sms.VerificacionEmailDTO;
import mx.com.tecnetia.marcoproyectoseguridad.service.VerificacionEmailService;
import mx.com.tecnetia.orthogonal.dto.IllegalArgumentExceptionDTO;
import mx.com.tecnetia.orthogonal.dto.UnAuthorizedDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/verificacion/email")
@Validated
@RequiredArgsConstructor
@Tag(name = "Verificación de email.", description = "Verificación de email del usuario.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Fallo en el parámetro de entrada.",
                content = @Content(schema = @Schema(implementation = IllegalArgumentExceptionDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado.", content = @Content(schema = @Schema(implementation = UnAuthorizedDTO.class)))})
public class VerificacionEmailController {
	
	private final VerificacionEmailService verificacionEmailService;
		
	@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Envia link de verificacion. LISTO", description = "Envia link de verificacion de email al usuario",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = MensajeDTO.class)))})
    @GetMapping(value = "/link/envia")
    public ResponseEntity<MensajeDTO<?>> enviarLinkVerificacion(@RequestParam("email") @NotEmpty String email) {
		var mensaje = verificacionEmailService.enviarLinkVerificacion(email);
		return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }
		
    @Operation(summary = "Valida link de verificacion. LISTO", description = "Valida link de verificacion de email del usuario")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = String.class)))})
    @GetMapping(value = "/link/valida")
    public ResponseEntity<String> validarLinkVerificacion(@RequestParam(name = "t", required = false) @Parameter(description = "Token del link de verificación.") String token) {
		String response = verificacionEmailService.validarLinkVerificacion(token);		
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Envia código de verificacion. LISTO", description = "Valida codigo de verificacion de email del usuario",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = MensajeDTO.class)))})
    @GetMapping(value = "/codigo/envia")
    public ResponseEntity<MensajeDTO<?>> enviarOTP(@RequestParam("email") @NotEmpty String email) {		
		var mensaje = verificacionEmailService.enviarOTP(email);
		return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }
	
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Valida código de verificacion. LISTO", description = "Valores de estatus: true, false, error",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = MensajeDTO.class)))})
	@PostMapping(value = "/codigo/valida")
    public ResponseEntity<MensajeDTO<?>> validarOTP(@RequestBody @Valid VerificacionEmailDTO dto) {		
		var mensaje = verificacionEmailService.validarOTP(dto.getEmail(), dto.getCodigo());
		return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

}
