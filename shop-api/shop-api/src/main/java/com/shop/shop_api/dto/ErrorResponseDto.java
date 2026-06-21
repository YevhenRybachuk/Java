package com.shop.shop_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Error response returned when an API request fails")
public class ErrorResponseDto {

    @Schema(description = "Date and time when the error occurred", example = "2026-05-23T14:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "HTTP error name", example = "Not Found")
    private String error;

    @Schema(description = "Human-readable error message", example = "Product with id 10 not found")
    private String message;

    @Schema(description = "Request path that caused the error", example = "/products/10")
    private String path;

    @Schema(description = "Validation error details", example = "[\"name: Product name is required\"]")
    private List<String> details;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
