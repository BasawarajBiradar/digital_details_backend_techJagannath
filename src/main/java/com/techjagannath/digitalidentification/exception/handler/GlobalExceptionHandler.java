package com.techjagannath.digitalidentification.exception.handler;

import com.techjagannath.digitalidentification.exception.AppException;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ValidationError;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─── Custom App Exceptions ───────────────────────────────────────────────

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(
            AppException ex, HttpServletRequest request) {

        log.error("App exception at [{} {}]: [{}] {}",
                request.getMethod(), request.getRequestURI(),
                ex.getCode(), ex.getMessage());

        return ResponseBuilder.error(ex.getMessage(), ex.getCode(), ex.getStatus());
    }

    // ─── Validation Exceptions ───────────────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> ValidationError.builder()
                        .field(err.getField())
                        .rejectedValue(err.getRejectedValue())
                        .message(err.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        log.warn("Validation failed at [{} {}]: {}",
                request.getMethod(), request.getRequestURI(), errors);

        return ResponseBuilder.validationError("Validation failed", errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request) {

        List<ValidationError> errors = ex.getConstraintViolations()
                .stream()
                .map(cv -> ValidationError.builder()
                        .field(cv.getPropertyPath().toString())
                        .rejectedValue(cv.getInvalidValue())
                        .message(cv.getMessage())
                        .build())
                .collect(Collectors.toList());

        log.warn("Constraint violation at [{} {}]: {}",
                request.getMethod(), request.getRequestURI(), errors);

        return ResponseBuilder.validationError("Constraint violation", errors);
    }

    // ─── Spring MVC Exceptions ───────────────────────────────────────────────

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        String message = "Method '" + ex.getMethod() + "' is not supported for this endpoint";
        log.warn("Method not supported at [{} {}]", request.getMethod(), request.getRequestURI());
        return ResponseBuilder.error(message, "METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {

        String message = "Media type '" + ex.getContentType() + "' is not supported";
        log.warn("Unsupported media type at [{} {}]", request.getMethod(), request.getRequestURI());
        return ResponseBuilder.error(message, "UNSUPPORTED_MEDIA_TYPE", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingParams(
            MissingServletRequestParameterException ex, HttpServletRequest request) {

        String message = "Required parameter '" + ex.getParameterName() + "' is missing";
        log.warn("Missing param at [{} {}]: {}", request.getMethod(), request.getRequestURI(), ex.getParameterName());
        return ResponseBuilder.error(message, "MISSING_PARAMETER", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        log.warn("Malformed request body at [{} {}]", request.getMethod(), request.getRequestURI());
        return ResponseBuilder.error(
                "Malformed or missing request body", "INVALID_REQUEST_BODY", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        String message = "Parameter '" + ex.getName() + "' should be of type "
                + ex.getRequiredType().getSimpleName();
        log.warn("Type mismatch at [{} {}]: {}", request.getMethod(), request.getRequestURI(), message);
        return ResponseBuilder.error(message, "TYPE_MISMATCH", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoHandlerFound(
            NoHandlerFoundException ex, HttpServletRequest request) {

        String message = "Route '" + ex.getRequestURL() + "' not found";
        log.warn("No handler at [{} {}]", request.getMethod(), request.getRequestURI());
        return ResponseBuilder.error(message, "ROUTE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    // ─── Security Exceptions ─────────────────────────────────────────────────

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(
            AccessDeniedException ex, HttpServletRequest request) {

        log.warn("Access denied at [{} {}]", request.getMethod(), request.getRequestURI());
        return ResponseBuilder.error(
                "You do not have permission to access this resource",
                "FORBIDDEN", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(
            BadCredentialsException ex, HttpServletRequest request) {

        log.warn("Bad credentials attempt at [{} {}]", request.getMethod(), request.getRequestURI());
        return ResponseBuilder.error(
                "Invalid email or password",
                "BAD_CREDENTIALS", HttpStatus.UNAUTHORIZED);
    }

// ─── Data Integrity Violation Exception ───────────────────────────────────

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, HttpServletRequest request) {

        log.error("Data integrity violation on request [{} {}]: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMostSpecificCause().getMessage());

        return ResponseBuilder.error(
                "Database constraint violation. Please check your input.",
                "DATA_INTEGRITY_VIOLATION",
                HttpStatus.BAD_REQUEST
        );
    }

    // ─── Fallback ────────────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(
            Exception ex, HttpServletRequest request) {

        log.error("Unhandled exception at [{} {}]: ",
                request.getMethod(), request.getRequestURI(), ex);

        return ResponseBuilder.error(
                "An unexpected error occurred",
                "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            ValidationException ex,
            HttpServletRequest request) {

        log.warn("Validation exception at [{} {}]: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage());

        return ResponseBuilder.error(
                ex.getMessage() != null && !ex.getMessage().isBlank()
                        ? ex.getMessage()
                        : "Validation failed",
                "VALIDATION_ERROR",
                HttpStatus.BAD_REQUEST
        );
    }
    
}
