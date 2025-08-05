package com.hermann.cepservice.exceptions;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException() {
        super("O intervalo de datas é inválido: a data de início não pode ser posterior à data de fim.");
    }
}
