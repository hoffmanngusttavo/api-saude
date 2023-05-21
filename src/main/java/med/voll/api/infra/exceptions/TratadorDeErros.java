package med.voll.api.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.model.service.exception.ServiceException;
import org.hibernate.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://github.com/brunocleite/spring-boot-exception-handling
 * */

@RestControllerAdvice
public class TratadorDeErros {



    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> entidadesRelacionadas(DataIntegrityViolationException exception, HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new StandardError.StandardErrorBuilder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.CONFLICT.value())
                        .errors(List.of("Objeto está relacionado a outro item"))
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException exception, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StandardError.StandardErrorBuilder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .errors(List.of("Resource not found"))
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }

    @ExceptionHandler({ServiceException.class, Exception.class})
    public ResponseEntity<StandardError> internalError(Exception exception, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StandardError.StandardErrorBuilder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errors(List.of("Ocorreu alguma falha ao processar requisição interna no servidor"))
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<StandardError> tratarErroBadCredentials(BadCredentialsException exception, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new StandardError.StandardErrorBuilder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .errors(List.of("Credenciais inválidas"))
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity tratarErroAcessoNegado(AccessDeniedException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new StandardError.StandardErrorBuilder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.FORBIDDEN.value())
                        .errors(List.of("Acesso negado"))
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResponseEntity<StandardError> authenticationService(InternalAuthenticationServiceException exception, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new StandardError.StandardErrorBuilder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.FORBIDDEN.value())
                        .errors(List.of("Acesso negado"))
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .build());

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErro400(HttpMessageNotReadableException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StandardError.StandardErrorBuilder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .errors(List.of(ex.getMessage()))
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> tratarErro400Ex4(MethodArgumentNotValidException ex, HttpServletRequest request){
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StandardError.StandardErrorBuilder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .errors(errors)
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build());
    }


    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<StandardError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String name = ex.getName();
        String type = ex.getRequiredType().getSimpleName();
        Object value = ex.getValue();
        String message = String.format("'%s' should be a valid '%s' and '%s' isn't", name, type, value);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StandardError.StandardErrorBuilder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .errors(List.of(message))
                        .message(ex.getMessage())
                        .path(request.getContextPath())
                        .build());
    }




    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ApiError> tratarErroValidacao(ValidacaoException ex){
        var apiError = new ApiError(HttpStatus.BAD_REQUEST, "Erro ao processar requisição", ex.getMessage());
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> tratarErro400Ex(MissingServletRequestParameterException ex){
        String error = ex.getParameterName() + " parameter is missing";
        var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ApiError> tratarErro400Ex2(TypeMismatchException ex){
        String error = ex.getMessage() + " to set bean property with the wrong type. ";
        var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }











    @Getter
    private class ApiError {

        private HttpStatus status;
        private int code;
        private String message;
        private List<String> errors;

        public ApiError(HttpStatus status, String message, List<String> errors) {
            super();
            this.status = status;
            this.code = status.value();
            this.message = message;
            this.errors = errors;
        }

        public ApiError(HttpStatus status, String message, String error) {
            super();
            this.status = status;
            this.code = status.value();
            this.message = message;
            errors = Arrays.asList(error);
        }
    }

    private record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao(ObjectError error){
            this(error.getObjectName(), error.getDefaultMessage());
        }

        public DadosErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }

    }

}
