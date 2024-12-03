package mx.com.tecnetia.orthogonal.api.rest;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HexFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.com.tecnetia.marcoproyectoseguridad.dto.catalogo.MensajeDTO;
import mx.com.tecnetia.orthogonal.dto.EditaUsuarioArquitecturaDTO;
import mx.com.tecnetia.orthogonal.dto.IllegalArgumentExceptionDTO;
import mx.com.tecnetia.orthogonal.dto.UnAuthorizedDTO;
import mx.com.tecnetia.orthogonal.security.EstatusUsuarioDTO;
import mx.com.tecnetia.orthogonal.security.JwtDTO;
import mx.com.tecnetia.orthogonal.security.LoginUsuarioDTO;
import mx.com.tecnetia.orthogonal.security.NuevoUsuarioArquitecturaDTO;
import mx.com.tecnetia.orthogonal.security.UsuarioPrincipal;
import mx.com.tecnetia.orthogonal.security.auth.AuthService;
import mx.com.tecnetia.orthogonal.security.auth.AuthenticationFacadeComponent;
import mx.com.tecnetia.orthogonal.services.UsuarioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/arq")
@Validated
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Gestión de Usuarios de arquitectura.", description = "Operaciones básicas con los usuarios. Servicios ortogonales.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Fallo en el parámetro de entrada.", content = @Content(schema = @Schema(implementation = IllegalArgumentExceptionDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado.", content = @Content(schema = @Schema(implementation = UnAuthorizedDTO.class)))})
public class UsuarioRestController {

    private final AuthService authService;
    private final AuthenticationFacadeComponent authenticationFacadeComponent;
    private final UsuarioService usuarioService;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Operation(summary = "Login de la aplicación. LISTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema = @Schema(implementation = JwtDTO.class)))})
    @PostMapping("/free/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginUsuarioDTO loginUsuario) {
        log.info("Login. Usuario entrando: {}", loginUsuario.getNick());
        JwtDTO jwtDTO = authService.login(loginUsuario);
        return new ResponseEntity<>(jwtDTO, HttpStatus.OK);
    }

    @Operation(summary = "Valida si el usuario ya existe y esta pre-registrado en la aplicación. LISTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema = @Schema(implementation = EstatusUsuarioDTO.class)))})
    @GetMapping("/free/existe-usuario")
    public ResponseEntity<EstatusUsuarioDTO> existeUsuario(@RequestParam("correo") @NotEmpty @Parameter(description = "Correo de usuario.") String correo) {
        log.info("Existe Usuario. Usuario entrando: {}", correo);
        EstatusUsuarioDTO existeUsuario = usuarioService.existeUsuario(correo);
        return new ResponseEntity<>(existeUsuario, HttpStatus.OK);
    }

    @Operation(summary = "Crea la contraseña para usuarios pre-registrados. LISTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema = @Schema(implementation = void.class)))})
    @PatchMapping(value = "/free/user-new-passw")
    public void creaPassw(@Valid @RequestBody LoginUsuarioDTO loginUsuario) {
        log.info("Creando passw para el usuario: {}", loginUsuario.getNick());
        this.usuarioService.editarContraseniaPendienteConfirmacion(loginUsuario.getNick(), loginUsuario.getPassw());
    }

    /*@Operation(summary = "Nuevo usuario de la aplicación. LISTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = Long.class)))})
    @PostMapping("/free/usuario")
    public ResponseEntity<Long> crearUsuario(@Valid @RequestBody NuevoUsuarioArquitecturaDTO user, @RequestPart(name = "file") MultipartFile file) {
        try {

            var ret = this.usuarioService.guardar(user, file.getBytes(), file.getOriginalFilename());
            return new ResponseEntity<>(ret.getIdArqUsuario(), HttpStatus.OK);
        } catch (IOException ex) {
            throw new IllegalArgumentException("No se puede leer el archivo TXT.");
        }
    }*/

    @Operation(summary = "Nuevo usuario de la aplicación. LISTO.", description = "CUALQUIER ROL. Valores de estatus: true, false, error")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = MensajeDTO.class)))})
    @PostMapping("/free/usuario")
    public ResponseEntity<MensajeDTO<?>> crearUsuario(@Valid @RequestBody NuevoUsuarioArquitecturaDTO user) {
        byte[] foto = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        var mensaje = this.usuarioService.crearUsuario(user, foto, "Prueba");
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }
    
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Edita datos del usuario. LISTO.", description = "CUALQUIER ROL. Valores de estatus: true, false, error",
    		   security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema =
            @Schema(implementation = MensajeDTO.class)))})
    @PatchMapping("/usuario/edita")
    public ResponseEntity<MensajeDTO<?>> editaUsuario(@Valid @RequestBody EditaUsuarioArquitecturaDTO datos) { 

        var mensaje = this.usuarioService.editarUsuario(datos);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Desactivar cuenta. LISTO.", description = "CUALQUIER ROL. Cambia el estatus del usuario a inactivo",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema = @Schema(implementation = void.class)))})
    @PatchMapping(value = "/user-disable")
    public void desactivarUsuario(@RequestParam("usr") @NotEmpty @Parameter(description = "Correo del usuario a deshabilitar.") String userMail) {
        this.usuarioService.desactivarUsuario(userMail);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Cambiar contraseña del usuario. LISTO.", description = "CUALQUIER ROL. Valores de estatus: true, false, error",
            security = {@SecurityRequirement(name = "security_auth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema = @Schema(implementation = MensajeDTO.class)))})
    @PatchMapping(value = "/usuario/contrasenia")
    public ResponseEntity<MensajeDTO<?>> editaContrsasenia(@RequestParam("passw") @NotEmpty @Parameter(description = "Nuevo passw.") String passw) {
        var usuarioLogeado = (UsuarioPrincipal) authenticationFacadeComponent.getAuthentication().getPrincipal();
        log.info("Email del usuario Logeado: {}", usuarioLogeado.getEmail());
        var mensaje = this.usuarioService.editarContrasenia(usuarioLogeado.getEmail(), passw);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @Operation(summary = "Recuperar contraseña. LISTO.", description = "CUALQUIER ROL. Recupera la contraseña de cualquier usuario registrado. Lo manda por correo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema = @Schema(implementation = void.class)))})
    @PatchMapping(value = "/free/user-passw-retrieve")
    public void recuperaContrasenia(@RequestParam("usr") @NotEmpty @Parameter(description = "Usuario a recuperar.") String user) {
        log.info("Email del usuario a recuperar: {}", user);
        this.usuarioService.recuperarContrasenia(user);
    }

    @Operation(summary = "Prueba de retorno de plantilla Thymeleaf. LISTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa.", content = @Content(schema = @Schema(implementation = String.class)))})
    @GetMapping("/free/test")
    public ResponseEntity<String> test() {
        LocalDate esteMomento = LocalDate.now();
        log.info("Fecha antes: {}", esteMomento.toString());
        String formattedString = esteMomento.format(BASIC_ISO_DATE);
        log.info("Fecha: {}", formattedString);
        log.info("Cambiado: {}", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        log.info("Cambiado: {}", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));//Este está mal

        var model = new HashMap<String, Object>();
        model.put("name", "Hola Pablito");
        var thymeleafContext = new Context();
        thymeleafContext.setVariables(model);
        var templateThymeleafFile = "confirmacion-email-thymeleaf.html";
        String htmlBody = thymeleafTemplateEngine.process(templateThymeleafFile, thymeleafContext);

        log.info("Test. Fin");
        return new ResponseEntity<>(htmlBody, HttpStatus.OK);
    }
}
