package com.taskmanagementsystem.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    OffsetDateTime timestamp;
    int status;           // HTTP status code, e.g., 400
    String error;         // Reason phrase, e.g., "Bad Request"
    String message;       // Human-readable message
    String path;          // Request path
    List<String> errors;  // Field-level or detail errors
}