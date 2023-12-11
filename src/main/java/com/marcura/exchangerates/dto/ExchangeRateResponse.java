package com.marcura.exchangerates.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExchangeRateResponse {
    private String from;
    private String to;
    private BigDecimal exchange;
}
