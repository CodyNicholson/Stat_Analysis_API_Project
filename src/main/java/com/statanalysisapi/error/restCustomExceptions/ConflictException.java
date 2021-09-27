package com.statanalysisapi.error.restCustomExceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ConflictException extends RuntimeException {
    private final String message;
    private final Throwable cause;
}
