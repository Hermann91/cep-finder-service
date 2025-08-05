package com.hermann.cepservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private int status;
}
