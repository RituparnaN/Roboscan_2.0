package com.quantumdataengines.app.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public abstract class ApplicationSubError {

}

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApplicationValidationError extends ApplicationSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApplicationValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
