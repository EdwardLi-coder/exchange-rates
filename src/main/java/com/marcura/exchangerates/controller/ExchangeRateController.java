package com.marcura.exchangerates.controller;

import com.marcura.exchangerates.dto.ApiResponse;
import com.marcura.exchangerates.dto.ExchangeRateResponse;
import com.marcura.exchangerates.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping
    public ResponseEntity<ApiResponse<ExchangeRateResponse>> calculateExchangeRate(@RequestParam String from, @RequestParam String to, @RequestParam(required = false) String date) {
        LocalDate parsedDate;
        if (date != null && !date.isEmpty()) {
            parsedDate = LocalDate.parse(date);
        } else {
            parsedDate = LocalDate.now();
        }

        ExchangeRateResponse result = exchangeRateService.calculateExchangeRate(from, to, parsedDate);
        return ResponseEntity.ok(ApiResponse.success(result));

    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateExchangeRates() {
        exchangeRateService.updateExchangeRates();
        return ResponseEntity.ok(ApiResponse.success("Exchange rates updated successfully"));
    }
}
