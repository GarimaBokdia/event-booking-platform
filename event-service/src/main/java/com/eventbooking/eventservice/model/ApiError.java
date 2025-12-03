package com.eventbooking.eventservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private boolean success;
    private ErrorDetails error;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetails {
        private String code;        // e.g., VENUE_NOT_FOUND
        private String message;     // user-friendly message
        private String details;     // optional technical details
        private String path;        // API path
        private LocalDateTime timestamp;
    }
}
