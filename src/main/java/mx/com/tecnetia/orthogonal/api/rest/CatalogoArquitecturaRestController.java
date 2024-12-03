package mx.com.tecnetia.orthogonal.api.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.orthogonal.dto.RolDTO;
import mx.com.tecnetia.orthogonal.dto.ServicioRestDTO;
import mx.com.tecnetia.orthogonal.services.CatalogoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/arq/catalogo")
@Validated
@RequiredArgsConstructor
@Tag(name = "Catálogos de arquitectura.", description = "Catálogos de arquitectura de la aplicación.")
public class CatalogoArquitecturaRestController {

    private final CatalogoService catalogoService;

    @Operation(summary = "Servicios REST.", description = "Todos los servicios REST de la aplicación. Envía un email de prueba.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = ServicioRestDTO.class)))})})
    @GetMapping("/free/servicios-rest")
    public ResponseEntity<List<ServicioRestDTO>> getServiciosRest() {
        var ret = catalogoService.getCatalogoServiciosRest();
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @Operation(summary = "Roles.", description = "Todos los roles de la aplicación.", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = RolDTO.class)))})})
    @GetMapping("/roles")
    public ResponseEntity<List<RolDTO>> getAllRoles() {
        var ret = catalogoService.getAllRoles();
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
