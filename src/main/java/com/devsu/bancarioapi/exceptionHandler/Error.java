package com.devsu.bancarioapi.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {

    private String field;
    private String message;

}
