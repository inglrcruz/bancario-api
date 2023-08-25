package com.devsu.bancarioapi.exceptionHandler;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponseErros {

    private int status;
    private String error;
    private String message;
    private List<Error> errors;

    public ErrorResponseErros(int status, String error, String message, List<Error> errors) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.errors = errors;
    }
}