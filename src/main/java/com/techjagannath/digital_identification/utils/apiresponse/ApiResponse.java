package com.techjagannath.digital_identification.utils.apiresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private String code;
    private T data;
    private List<ValidationError> errors;   // ← for field-level validation errors

    @Builder.Default
    private Instant timestamp = Instant.now();
}
