package mx.com.tecnetia.orthogonal.utils;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionsManager extends ResponseEntityExceptionHandler {
	
    private static final String MENSAJE = "mensaje";
    private static final String TIME = "localdatetime";
    private static final String STATUS = "status";
    private static final String MSG_GENERICO = "Error.";

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException ex,
                                                                  org.springframework.http.HttpHeaders headers,
                                                                  org.springframework.http.HttpStatusCode status,
                                                                  org.springframework.web.context.request.WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIME, LocalDateTime.now());
        body.put(STATUS, status.value());
        // Get all errors:
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        var erroresStr =  errors.stream().reduce("", (partialString, element) -> partialString + element);
        body.put(MENSAJE, erroresStr);
        log.error("MethodArgumentNotValidException: {}", erroresStr);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleConflict(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIME, LocalDateTime.now());
        body.put(STATUS, 400);
        body.put(MENSAJE, ex.getLocalizedMessage() == null ? MSG_GENERICO : ex.getLocalizedMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.set(MENSAJE, ex.getLocalizedMessage() == null ? MSG_GENERICO : ex.getLocalizedMessage());
        log.error("IllegalArgumentException: {}", ex.getLocalizedMessage() == null ? MSG_GENERICO : ex.getLocalizedMessage());
        return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = java.lang.IllegalStateException.class)
    protected ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIME, LocalDateTime.now());
        body.put(STATUS, 400);
        body.put(MENSAJE, ex.getLocalizedMessage() == null ? MSG_GENERICO : ex.getLocalizedMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.set(MENSAJE, ex.getLocalizedMessage() == null ? MSG_GENERICO : ex.getLocalizedMessage());
        log.error("IllegalStateException: {}", ex.getLocalizedMessage() == null ? MSG_GENERICO : ex.getLocalizedMessage());
        return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleConflictAll(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIME, LocalDateTime.now());
        body.put(STATUS, 400);
        body.put(MENSAJE, ex.getLocalizedMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.set(MENSAJE, ex.getLocalizedMessage());
        log.error("RuntimeException: {}", ex.getLocalizedMessage() == null ? MSG_GENERICO : ex.getLocalizedMessage());
        ex.printStackTrace();
        return handleExceptionInternal(ex, body, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = org.springframework.dao.DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIME, LocalDateTime.now());
        body.put(STATUS, 409);
        body.put(MENSAJE, "Intenta insertar un registro que ya existe en la BD.");
        HttpHeaders headers = new HttpHeaders();
        headers.set(MENSAJE, ex.getLocalizedMessage());
        log.error("DataIntegrityViolationException: {}", ex.getLocalizedMessage() == null ? MSG_GENERICO : ex.getLocalizedMessage());
        return handleExceptionInternal(ex, body, headers, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = org.springframework.security.access.AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIME, LocalDateTime.now());
        body.put(STATUS, 403);
        body.put(MENSAJE, "Acceso denegado al recurso");
        HttpHeaders headers = new HttpHeaders();
        headers.set(MENSAJE, ex.getLocalizedMessage());
        log.error("Acceso denegado: {}", ex.getLocalizedMessage() == null ? MSG_GENERICO : ex.getLocalizedMessage());
        return handleExceptionInternal(ex, body, headers, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc,
                                                         WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIME, LocalDateTime.now());
        body.put(STATUS, 400);
        body.put(MENSAJE, "Archivo demasiado grande: " + exc.getLocalizedMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.set(MENSAJE, exc.getLocalizedMessage());
        return handleExceptionInternal(exc, body, headers, HttpStatus.EXPECTATION_FAILED, request);
    }
}
