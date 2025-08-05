package com.hermann.cepservice.exceptions;


public class InvalidCepFormatException extends RuntimeException {
    public InvalidCepFormatException(String cep) {
        super("CEP inválido: " + cep + ". O CEP deve conter exatamente 8 dígitos numéricos.");
    }
}

