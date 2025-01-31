package mx.com.tecnetia.marcoproyectoseguridad.recargas.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.RespuestaCuponCanjeadoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.dto.DenominacionRecargaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.dto.RecargaDTO;
import mx.com.tecnetia.marcoproyectoseguridad.recargas.service.RecargaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2025-01-23
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/recarga")
@Validated
@RequiredArgsConstructor
@Tag(name = "Recargas", description = "Servicios de recarga de celulares")
public class RecargaCelRestController {
    private final RecargaService recargaService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Denominaciones", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = DenominacionRecargaDTO.class)))})})
    @GetMapping("/denominaciones")
    public ResponseEntity<List<DenominacionRecargaDTO>> getAllDenominaciones() {
        var ret = this.recargaService.getAllDenominacionRecargaCel();
        return ResponseEntity.ok().body(ret);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Recarga.",
            description = "Registrar recarga",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.",
                    content = @Content(schema = @Schema(implementation = RespuestaCuponCanjeadoDTO.class))),
            @ApiResponse(responseCode = "4xx/5xx", description = "Operación fallida.",
                    content = @Content(schema = @Schema(implementation = IllegalArgumentException.class)))})
    @PostMapping(value = "/")
    public ResponseEntity<RespuestaCuponCanjeadoDTO> registrar(@RequestBody @NotNull @Valid RecargaDTO recargaDTO) {
        var ret = this.recargaService.registrarRecarga(recargaDTO);
        return ResponseEntity.ok().body(ret);
    }
}
