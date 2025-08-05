package com.hermann.cepservice.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String firstError = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Erro de validação");

        return buildErrorResponse(firstError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
        return buildErrorResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CepNotFoundException.class)
    public ResponseEntity<Object> handleCepNotFound(CepNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        return buildErrorResponse("Erro interno. Contate o suporte.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidCepFormatException.class)
    public ResponseEntity<Object> handleInvalidCepFormat(InvalidCepFormatException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<Object> handleInvalidDateRange(InvalidDateRangeException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleDateFormatError(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() == LocalDate.class) {
            return buildErrorResponse(
                    "Formato de data inválido. Use o padrão yyyy-MM-dd.",
                    HttpStatus.BAD_REQUEST
            );
        }
        return buildErrorResponse("Parâmetro inválido: " + ex.getName(), HttpStatus.BAD_REQUEST);
    }




    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        ErrorResponse error = new ErrorResponse(
                status.getReasonPhrase(),
                message,
                LocalDateTime.now(),
                status.value()
        );
        return new ResponseEntity<>(error, status);
    }

}
