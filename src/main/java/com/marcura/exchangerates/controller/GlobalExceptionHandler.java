package com.marcura.exchangerates.controller;

import com.marcura.exchangerates.dto.ApiResponse;
import com.marcura.exchangerates.exception.ExchangeRateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExchangeRateNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleExchangeRateNotFound(ExchangeRateNotFoundException ex) {
        return new ResponseEntity<>(ApiResponse.failure(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
