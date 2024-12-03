package mx.com.tecnetia.orthogonal.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.orthogonal.services.NotificacionesService;
import mx.com.tecnetia.orthogonal.services.UsuarioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/arq/notificaciones")
@Validated
@RequiredArgsConstructor
@Tag(name = "Notificaciones push.", description = "Servicios de notificaciones push para los dispositivos de los usuarios.")
public class NotificacionesRestController {

    private final NotificacionesService notificacionesService;
    private final UsuarioService usuarioService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Guarda el token del dispositivo del usuario. LISTO.",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = void.class)))})
    @PostMapping(value="/token")
    public ResponseEntity<Void> guardaToken(@RequestParam("token") String token) {
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        this.notificacionesService.guardarToken(idUsuarioLogueado,token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
