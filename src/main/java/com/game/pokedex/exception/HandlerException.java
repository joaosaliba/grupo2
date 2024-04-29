package com.game.pokedex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ExceptionDTO handleGeneralException(Exception exception) {
        return new ExceptionDTO(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    record ExceptionDTO(String messege, HttpStatus status) {
    }
}
