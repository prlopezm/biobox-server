package mx.com.tecnetia.marcoproyectoseguridad.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.FotoProductoRecicladoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.dto.reciclaje.FotoProductoRecicladoListDTO;
import mx.com.tecnetia.marcoproyectoseguridad.service.ImagenesService;
import mx.com.tecnetia.orthogonal.dto.IllegalArgumentExceptionDTO;
import mx.com.tecnetia.orthogonal.dto.UnAuthorizedDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/imagen")
@Validated
@RequiredArgsConstructor
@Tag(name = "Imagenes.", description = "Servicios para la consulta de imagenes.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Fallo en el parámetro de entrada.",
                content = @Content(schema = @Schema(implementation = IllegalArgumentExceptionDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado.", content = @Content(schema = @Schema(implementation = UnAuthorizedDTO.class)))})
public class ImagenesRestController {

	private final ImagenesService imagenesService;
	
	@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Consulta la imagen de un producto reciclado.",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = FotoProductoRecicladoDTO.class)))})
    @GetMapping(value="/consulta-foto")
    public ResponseEntity<FotoProductoRecicladoDTO> consultarFotoProductoReciclado(@Valid @RequestParam Long id) {        
		FotoProductoRecicladoDTO dto = this.imagenesService.consultarFotoProductoReciclado(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
	
	@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Consulta de imagenes de productos reciclados. (Separar)",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = FotoProductoRecicladoListDTO.class)))})
    @GetMapping(value="/consulta-fotos")
    public ResponseEntity<FotoProductoRecicladoListDTO> consultaFotos(@Valid @Parameter( description = "Separar cada id con una coma. Ejemplo: ids = 1,2,3 ")
    																		 @RequestParam String ids) {
		List<Long> idList = new ArrayList<>();
		Arrays.asList(ids.split(",")).forEach(id->{
			idList.add(Long.valueOf(id));
		});		
    	FotoProductoRecicladoListDTO dto = this.imagenesService.consultarFotosProductosReciclados(idList);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }	
	
}
