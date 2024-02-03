package com.reverb.editor.exceptions;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
@Slf4j
public class EditorExceptionHandler {

    @ExceptionHandler(value = EditorServiceException.class)
    public ResponseEntity<ErrorResponse> handlerForAppManagerServiceException(EditorServiceException ex) {

        if (ex.getErrorCode() == 0) {
            ex.setErrorCode(HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getErrorCode(),ex.getMessage()),
                HttpStatus.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerValidationException(MethodArgumentNotValidException ex) {
        Map<String,String> errorResults=new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .forEach(fieldError -> errorResults.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorResults.values().toString()),
                HttpStatus.BAD_REQUEST);
    }



}
