package com.techjagannath.digital_identification.utils.apiresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {
    private String field;
    private Object rejectedValue;
    private String message;
}
