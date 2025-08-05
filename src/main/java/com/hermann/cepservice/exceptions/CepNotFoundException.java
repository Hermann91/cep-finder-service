package com.hermann.cepservice.exceptions;

public class CepNotFoundException extends RuntimeException {
    public CepNotFoundException(String message) {
        super(message);
    }
}
