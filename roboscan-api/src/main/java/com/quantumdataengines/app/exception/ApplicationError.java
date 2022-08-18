package com.quantumdataengines.app.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime timestamp;
    private String message;
    private String description;
    private List<ApplicationSubError> subErrors;

    private ApplicationError() {
        timestamp = LocalDateTime.now();
    }

    public ApplicationError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        if(ex != null) {
        	this.description = ex.getLocalizedMessage();
        }
    }
}
