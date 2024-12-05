package mx.com.tecnetia.marcoproyectoseguridad.oxxo.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.CanjeDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.MenuOxxoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.dto.OpcionCanjeOxxoDTO;
import mx.com.tecnetia.marcoproyectoseguridad.oxxo.service.MenuOxxoService;
import mx.com.tecnetia.orthogonal.dto.IllegalArgumentExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private final MenuOxxoService oxxoService;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "P00. Menú de Oxxo.",
            description = "Verifica si el celular está activo en Oxxo. Si no está activo, le envía un error. " +
                    "Si está activo, envía el menú",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Celular activo.", content = @Content(schema =
            @Schema(implementation = MenuOxxoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Celular inactivo.", content = @Content(schema =
            @Schema(implementation = IllegalArgumentExceptionDTO.class)))})
    @GetMapping(value = "/menu")
    public ResponseEntity<MenuOxxoDTO> menuOxxo() {
        var ret = this.random(2);
/*        if(ret == 0){
            //Simula usuario no activo en Oxxo:
            throw new IllegalArgumentException("Para acceder a los beneficios Spin, por favor regístrate https://spinpremia.com/");
        }
        var menu = this.creaMenu();*/
        var menu = oxxoService.obtenerMenu();
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "P01. Canje de puntos.",
            description = "Si el usuario tiene los suficientes puntos",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa. Puntos canjeados", content = @Content(schema =
            @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Operación fallida. Puntos no canjeados porque los puntos no le alcanzan o porque " +
                    "hubo un problema con Oxxo", content = @Content(schema =
            @Schema(implementation = IllegalArgumentExceptionDTO.class)))})
    @PostMapping(value = "/canje")
    public ResponseEntity<String> canjePuntos(@RequestBody @NotNull @Valid CanjeDTO canjeDTO) {
        var rnd = this.random(3);
        if (rnd == 0) {//Puntos no alcanzan
            throw new IllegalArgumentException("Has alcanzado el límite mensual de los beneficios Spin. Te invitamos a volver a partir " +
                    "del 1er día del mes siguiente para seguir disfrutando de estos beneficios");
        } else if (rnd == 1) {//Problema con Oxxo
            throw new IllegalArgumentException("Ups, hay un problema con Oxxo, llama a César a ver qué onda!!!");
        }
        return new ResponseEntity<>("Puntos canjeados correctamente. Te enviaremos un email", HttpStatus.OK);
    }

    private int random(int max) {
        var r = new Random();
        return r.nextInt(max);
    }

    private MenuOxxoDTO creaMenu() {
        var opcion0 = new OpcionCanjeOxxoDTO()
                .setId(1)
                .setNombre("Cambia 5 puntos BioBox por 5 puntos Oxxo");
        var opcion1 = new OpcionCanjeOxxoDTO()
                .setId(2)
                .setNombre("Cambia 10 puntos BioBox por 10 puntos Oxxo");
        return new MenuOxxoDTO()
                .setLeyenda("Limitado a 100 puntos Spin por usuario cada mes")
                .setPuntosRestantes(20)
                .setOpciones(List.of(opcion0, opcion1));
    }

}
