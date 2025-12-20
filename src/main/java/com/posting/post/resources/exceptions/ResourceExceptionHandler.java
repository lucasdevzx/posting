package com.posting.post.resources.exceptions;

import java.time.Instant;
import java.util.List;

import com.posting.post.services.exceptions.ConflictException;
import com.posting.post.services.exceptions.UnauthorizedActionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.posting.post.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    // Recursos
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionsStandard> ResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String error = "Resource Not Found";
        ExceptionsStandard standard = new ExceptionsStandard(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(standard);
    }

    // Validação Parâmetros -> Bean Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionStandard> ValidationException(MethodArgumentNotValidException e) {
        List<FieldErrorResponse> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldErrorResponse(
                        fieldError.getField(),
                        fieldError.getDefaultMessage())
                ).toList();

        ValidationExceptionStandard standart = new ValidationExceptionStandard(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standart);
    }

    // Permissão
    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ExceptionsStandard> UnauthorizedAction(UnauthorizedActionException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String error = "Acess Not Permitted!";
        ExceptionsStandard standard = new ExceptionsStandard(
                Instant.now(),
                status.value(),
                error,
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(standard);
    }

    // Regra de Negócio
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionsStandard> ConflictException(ConflictException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Conflict Data";
        ExceptionsStandard standard = new ExceptionsStandard(
                Instant.now(),
                status.value(),
                error,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(standard);
    }
}
