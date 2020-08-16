package com.github.pedrobacchini.advice;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ResourceExceptionHandler {

    public static final String INVALID_FIELD_MESSAGE = "Invalid field";
    public static final String MALFORMED_JSON_MESSAGE = "Malformed JSON";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> onHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

        return getResponseEntity(MALFORMED_JSON_MESSAGE, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<FieldValidationError> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return getResponseEntity(INVALID_FIELD_MESSAGE, errors);
    }

    private ResponseEntity<Object> getResponseEntity(String message, List<FieldValidationError> detailedErrors) {

        Map<String, Object> errorResult = new HashMap<>(Map.of("message", message));

        if (detailedErrors != null && !detailedErrors.isEmpty()) {
            errorResult.put("errors", detailedErrors);
        }

        log.warn(errorResult.toString());

        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @Data
    static class FieldValidationError {
        private final String field;
        private final String detail;
    }
}
