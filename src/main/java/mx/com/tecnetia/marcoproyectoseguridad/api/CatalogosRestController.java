package mx.com.tecnetia.marcoproyectoseguridad.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AnunciosListDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.AyudasListDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.LineasListDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.QuioscosListDTO;
import mx.com.tecnetia.marcoproyectoseguridad.service.CatalogosService;
import mx.com.tecnetia.orthogonal.dto.IllegalArgumentExceptionDTO;
import mx.com.tecnetia.orthogonal.dto.UnAuthorizedDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/catalogos")
@Validated
@RequiredArgsConstructor
@Tag(name = "Catálogos.", description = "Catálogos de la aplicación.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Fallo en el parámetro de entrada.",
                content = @Content(schema = @Schema(implementation = IllegalArgumentExceptionDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado.", content = @Content(schema = @Schema(implementation = UnAuthorizedDTO.class)))})
public class CatalogosRestController {
    private final CatalogosService catalogosService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Lineas y estaciones del metro. LISTO", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = LineasListDTO.class)))})
    @GetMapping("/lineas-metro")
    public ResponseEntity<LineasListDTO> getLineasMetro() {
        var ret = catalogosService.getAllLineasMetro();
        LineasListDTO lineas = new LineasListDTO();
        lineas.setLineas(ret);
        return new ResponseEntity<>(lineas, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Ayudas. LISTO", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = AyudasListDTO.class)))})
    @GetMapping("/ayudas")
    public ResponseEntity<AyudasListDTO> getAyudas() {
        var ret = catalogosService.getAllAyudas();
        AyudasListDTO ayudas = new AyudasListDTO();
        ayudas.setAyudas(ret);
        return new ResponseEntity<>(ayudas, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Anuncios. LISTO", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = AnunciosListDTO.class)))})
    @GetMapping("/anuncios")
    public ResponseEntity<AnunciosListDTO> getAnuncios() {
        var ret = catalogosService.getAllAnuncios();
        AnunciosListDTO anuncios = new AnunciosListDTO();
        anuncios.setAnuncios(ret);
        return new ResponseEntity<>(anuncios, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Anuncios educativos. LISTO", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = AnunciosListDTO.class)))})
    @GetMapping("/anuncios-educativos")
    public ResponseEntity<AnunciosListDTO> getAnunciosEducativos() {
        var ret = catalogosService.getAllAnunciosEducativos();
        AnunciosListDTO anuncios = new AnunciosListDTO();
        anuncios.setAnuncios(ret);
        return new ResponseEntity<>(anuncios, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Quioscos. LISTO", security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa.",
            content = @Content(schema = @Schema(implementation = QuioscosListDTO.class)))})
    @GetMapping("/quioscos")
    public ResponseEntity<QuioscosListDTO> getQuioscos() {
        var ret = catalogosService.getAllQuioscos();
        QuioscosListDTO quioscos = new QuioscosListDTO();
        quioscos.setQuioscos(ret);
        return new ResponseEntity<>(quioscos, HttpStatus.OK);
    }
}
