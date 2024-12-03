package mx.com.tecnetia.marcoproyectoseguridad.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.metro.QrMetroDTO;
import mx.com.tecnetia.marcoproyectoseguridad.service.MetroService;
import mx.com.tecnetia.orthogonal.dto.IllegalArgumentExceptionDTO;
import mx.com.tecnetia.orthogonal.dto.UnAuthorizedDTO;
import mx.com.tecnetia.orthogonal.services.UsuarioService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/metro")
@Validated
@RequiredArgsConstructor
@Tag(name = "STC Metro.", description = "Sistema de transporte colectivo metro.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Fallo en el parámetro de entrada.",
                content = @Content(schema = @Schema(implementation = IllegalArgumentExceptionDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado.", content = @Content(schema = @Schema(implementation = UnAuthorizedDTO.class)))})
public class MetroRestController {
    private final MetroService metroService;
    private final UsuarioService usuarioService;

    @PreAuthorize("hasAuthority('ROLE_RECICLADOR')")
    @Operation(summary = "QRs generados. LISTO", description = "ROLE_RECICLADOR. Guarda los QRs generados por un usuario en el metro",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = void.class)))})
    @PostMapping(value = "/qrs-generados")
    public ResponseEntity<Void> guardarQrsGenerados(@RequestBody @NotEmpty List<@NotNull @Valid QrMetroDTO> qrs) {
        var idUsuarioLogueado = this.usuarioService.getUsuarioLogeado().getIdArqUsuario();
        this.metroService.saveNuevosQr(qrs, idUsuarioLogueado);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
