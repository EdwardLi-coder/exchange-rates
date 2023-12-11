package com.marcura.exchangerates.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class FixerResponse {
    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
}
