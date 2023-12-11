package com.marcura.exchangerates.service;

import com.marcura.exchangerates.annotation.Metric;
import com.marcura.exchangerates.dto.ExchangeRateResponse;
import com.marcura.exchangerates.dto.FixerResponse;
import com.marcura.exchangerates.entity.ExchangeRate;
import com.marcura.exchangerates.exception.ExchangeRateNotFoundException;
import com.marcura.exchangerates.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Value("${fixer.io.api.url}")
    private String fixerIoApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Metric
    public ExchangeRateResponse calculateExchangeRate(String fromCurrency, String toCurrency, LocalDate date) {
        ExchangeRate fromRate = exchangeRateRepository.findByCurrencyCodeAndRateDate(fromCurrency, date)
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found for " + fromCurrency));

        ExchangeRate toRate = exchangeRateRepository.findByCurrencyCodeAndRateDate(toCurrency, date)
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found for " + toCurrency));

        BigDecimal spread = getHigherSpread(fromRate.getSpreadPercentage(), toRate.getSpreadPercentage());
        BigDecimal exchangeRate = toRate.getExchangeRate().divide(fromRate.getExchangeRate(), MathContext.DECIMAL128)
                .multiply(BigDecimal.valueOf(100).subtract(spread))
                .divide(BigDecimal.valueOf(100), MathContext.DECIMAL128);
        return new ExchangeRateResponse(fromCurrency, toCurrency, exchangeRate);
    }

    private BigDecimal getHigherSpread(BigDecimal fromSpread, BigDecimal toSpread) {
        return fromSpread.max(toSpread);
    }

    @Scheduled(cron = "0 5 0 * * *") // Runs at 12:05 AM GMT every day
    @Transactional
    public void updateExchangeRates() {
        LocalDate today = LocalDate.now();
        FixerResponse response = restTemplate.getForObject(fixerIoApiUrl, FixerResponse.class);
        System.err.println("response = " + response);
        if (response != null && response.getRates() != null) {
            response.getRates().forEach((currency, rate) -> {
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setCurrencyCode(currency);
                exchangeRate.setExchangeRate(rate);
                exchangeRate.setRateDate(today);
                exchangeRate.setSpreadPercentage(determineSpreadPercentage(currency));
                exchangeRateRepository.save(exchangeRate);
            });
        }
    }

    private BigDecimal determineSpreadPercentage(String currencyCode) {
        switch (currencyCode) {
            case "JPY":
            case "HKD":
            case "KRW":
                return BigDecimal.valueOf(3.25);
            case "MYR":
            case "INR":
            case "MXN":
                return BigDecimal.valueOf(4.50);
            case "RUB":
            case "CNY":
            case "ZAR":
                return BigDecimal.valueOf(6.00);
            default:
                return BigDecimal.valueOf(2.75);
        }
    }

}
