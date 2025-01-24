package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponCanjeadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.CuponDTO;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service.CuponeraService;
import mx.com.tecnetia.orthogonal.dto.IllegalArgumentExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-17
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cuponerapp")
@Validated
@RequiredArgsConstructor
@Tag(name = "CuponerApp", description = "Servicios de integración con CuponerApp")
public class CuponeraRestController {
    private final CuponeraService cuponeraService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Cupones", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CuponDTO.class)))})})
    @GetMapping("/cupones")
    public ResponseEntity<List<CuponDTO>> getTodosCupones() {
        var ret = cuponeraService.getCupones();
        return ResponseEntity.ok().body(ret);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Canje.",
            description = "Canje de un cupón seleccionado",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.",
                    content = @Content(schema = @Schema(implementation = CuponCanjeadoDTO.class))),
            @ApiResponse(responseCode = "4xx/5xx", description = "Operación fallida.",
                    content = @Content(schema = @Schema(implementation = IllegalArgumentException.class)))})
    @PostMapping(value = "/canje")
    public ResponseEntity<CuponCanjeadoDTO> canjePuntos(@RequestParam("promo-id") @NotNull Integer promoId) {
        var ret = this.cuponeraService.canjear(promoId);
        return ResponseEntity.ok().body(ret);
    }
}
