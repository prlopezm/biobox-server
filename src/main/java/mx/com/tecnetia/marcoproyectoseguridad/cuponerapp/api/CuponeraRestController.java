package mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.api;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.dto.*;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service.CatalogosCuponeraService;
import mx.com.tecnetia.marcoproyectoseguridad.cuponerapp.service.CuponeraService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    private final CatalogosCuponeraService catalogosCuponeraService;

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
                    content = @Content(schema = @Schema(implementation = RespuestaCuponCanjeadoDTO.class))),
            @ApiResponse(responseCode = "4xx/5xx", description = "Operación fallida.",
                    content = @Content(schema = @Schema(implementation = IllegalArgumentException.class)))})
    @PostMapping(value = "/canje")
    public ResponseEntity<RespuestaCuponCanjeadoDTO> canjePuntos(@RequestBody @NotNull @Valid RespuestaCuponeraDTO respuestaCuponeraDTO) {
        var ret = this.cuponeraService.canjear(respuestaCuponeraDTO);
        return ResponseEntity.ok().body(ret);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Cupones canjeados", description = "Fechas en formato yyyy-MM-dd: 2025-12-25",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CuponCanjeadoDTO.class)))})})
    @GetMapping("/canjeados")
    public ResponseEntity<List<CuponCanjeadoDTO>> getCuponesCanjeados(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @JsonFormat(pattern = "yyyy-MM-dd") @RequestParam("fecha-inicio") LocalDate fechaInicio,
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @JsonFormat(pattern = "yyyy-MM-dd") @RequestParam("fecha-fin") LocalDate fechaFin) {
        var ret = cuponeraService.cuponesCanjeadosEntreFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok().body(ret);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Estados. LISTO", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = EstadoDTO.class)))})
    @GetMapping("/estados")
    public ResponseEntity<List<EstadoDTO>> getEstados() {
        return new ResponseEntity<>(catalogosCuponeraService.getEstados(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Estados Categorias. LISTO", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = EstadoDTO.class)))})
    @GetMapping("/estados/categorias")
    public ResponseEntity<List<EstadoCategoriaDTO>> getEstadosCategorias() {
        return new ResponseEntity<>(catalogosCuponeraService.getEstadosCategorias(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Categorias. LISTO", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = CategoriaDTO.class)))})
    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> getCategoria() {
        return new ResponseEntity<>(catalogosCuponeraService.getCategorias(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Cuponera By CategoriaAndEstado. LISTO", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = CuponDTO.class)))})
    @GetMapping("/cuponesapp")
    public ResponseEntity<List<CuponDTO>> getCuponesByCategoriaAndEstado(@RequestParam Integer idCategoria,
                                                                       @RequestParam Integer idEstado) {
        var ret = this.cuponeraService.cuponesByCategoriaAndEstado(idCategoria, idEstado);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
