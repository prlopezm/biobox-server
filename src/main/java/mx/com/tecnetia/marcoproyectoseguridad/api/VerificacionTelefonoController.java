package mx.com.tecnetia.marcoproyectoseguridad.api;

import lombok.extern.log4j.Log4j2;
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
import mx.com.tecnetia.marcoproyectoseguridad.dto.sms.VerificacionTelefonoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.service.VerificacionTelefonoService;
import mx.com.tecnetia.orthogonal.dto.IllegalArgumentExceptionDTO;
import mx.com.tecnetia.orthogonal.dto.UnAuthorizedDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/verificacion/telefono")
@Validated
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Verificación de telefono.", description = "Verificación de telefono del usuario.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Fallo en el parámetro de entrada.",
                content = @Content(schema = @Schema(implementation = IllegalArgumentExceptionDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado.", content = @Content(schema = @Schema(implementation = UnAuthorizedDTO.class)))})
public class VerificacionTelefonoController {

	private final VerificacionTelefonoService verificacionTelefonoService;
	
	@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Envia código de verificacion. LISTO", description = "Envia código de verificacion de email al usuario",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = MensajeDTO.class)))})
    @GetMapping(value = "/codigo/envia")
    public ResponseEntity<MensajeDTO<?>> enviarOTP(@RequestParam("telefono") @NotEmpty String telefono) {
        log.info("/codigo/envia. Teléfono a validar: {}", telefono);
		var mensaje = verificacionTelefonoService.enviarOTPporSMS(telefono);
		return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }
	
	 @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Valida código de verificacion. LISTO", description = "Valores de estatus: true, false, error",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = MensajeDTO.class)))})
	@PostMapping(value = "/codigo/valida")
    public ResponseEntity<MensajeDTO<?>> validarOTP(@RequestBody @Valid VerificacionTelefonoDTO dto) {
        log.info("/codigo/valida. Código a validar: {}", dto);
		var mensaje = verificacionTelefonoService.validarOTPporSMS(dto.getTelefono(), dto.getCodigo());
		return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }
	
}
