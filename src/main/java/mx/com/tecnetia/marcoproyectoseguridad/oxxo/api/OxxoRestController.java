package mx.com.tecnetia.marcoproyectoseguridad.oxxo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.MensajeOxxoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.MenuOxxoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.OpcionCanjeOxxoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @author Pablo Rafael López Martínez <prlopezm@gmail.com>
 * @version 1.0 2024-12-03
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/oxxo")
@Validated
@RequiredArgsConstructor
@Tag(name = "Oxxo", description = "Servicios de Oxxo")
public class OxxoRestController {

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "¿Está activo el celular en Oxxo?.",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TRUE si el celular está activo en Oxxo. FALSE en caso contrario.", content = @Content(schema =
            @Schema(implementation = MensajeOxxoDTO.class)))})
    @GetMapping(value = "/celular_activo")
    public ResponseEntity<MensajeOxxoDTO> isOxxoActivo() {
        var ret = this.random(2);
        var msg = new MensajeOxxoDTO()
                .setUrl("https://spinpremia.com")
                .setActivo(ret != 0);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Menú de Oxxo.",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Respuesta exitosa.", content = @Content(schema =
            @Schema(implementation = MenuOxxoDTO.class)))})
    @GetMapping(value = "/menu")
    public ResponseEntity<MenuOxxoDTO> menuOxxo() {
        var menu = this.creaMenu();
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    private int random(int max) {
        var r = new Random();
        return r.nextInt(max);
    }

    private MenuOxxoDTO creaMenu() {
        var opcion0 = new OpcionCanjeOxxoDTO()
                .setId(1)
                .setNombre("Cambia 5 puntos");
        var opcion1 = new OpcionCanjeOxxoDTO()
                .setId(2)
                .setNombre("Cambia 10 puntos");
        return new MenuOxxoDTO().setPuntosRestantes(20)
                .setOpciones(List.of(opcion0, opcion1));
    }

}
