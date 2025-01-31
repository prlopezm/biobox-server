package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.HistoricoCanjeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service.CanjeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-31 11:33 AM
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/canjes")
@Validated
@RequiredArgsConstructor
@Tag(name = "Canjes", description = "Canjes distintos a prontipagos. Oxxo, recargas cel y cupones")
public class CanjeRestController {
    private final CanjeService canjeService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Histórico", description = "Histórico de canjes de Oxxo, recargas cel y cupones",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = HistoricoCanjeDTO.class)))})})
    @GetMapping("/canjeados")
    public ResponseEntity<List<HistoricoCanjeDTO>> getCuponesCanjeados() {
        var ret = canjeService.usadasPorUsuarioFirmado();
        return ResponseEntity.ok().body(ret);
    }
}
