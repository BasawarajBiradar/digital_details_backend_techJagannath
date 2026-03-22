package com.techjagannath.digital_identification.utils.apiresponse;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@UtilityClass
public class ResponseBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .code("SUCCESS")
                .data(data)
                .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<T>builder()
                        .success(true)
                        .message(message)
                        .code("CREATED")
                        .data(data)
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.<T>builder()
                        .success(true)
                        .message("No content")
                        .code("NO_CONTENT")
                        .build());
    }

    // For simple errors (no field errors)
    public static <T> ResponseEntity<ApiResponse<T>> error(String message, String code, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(ApiResponse.<T>builder()
                        .success(false)
                        .message(message)
                        .code(code)
                        .build());
    }

    // For validation errors (with field-level errors)
    public static <T> ResponseEntity<ApiResponse<T>> validationError(
            String message, List<ValidationError> errors) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.<T>builder()
                        .success(false)
                        .message(message)
                        .code("VALIDATION_FAILED")
                        .errors(errors)
                        .build());
    }
}
