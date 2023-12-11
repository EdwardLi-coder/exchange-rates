package com.marcura.exchangerates.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "exchange_rate", nullable = false, precision = 15, scale = 6)
    private BigDecimal exchangeRate;

    @Column(name = "rate_date", nullable = false)
    private LocalDate rateDate;

    @Column(name = "spread_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal spreadPercentage;
}
