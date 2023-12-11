package com.marcura.exchangerates.service;

import com.marcura.exchangerates.entity.UsageCounter;
import com.marcura.exchangerates.repository.UsageCounterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UsageCounterService {

    @Autowired
    private UsageCounterRepository usageCounterRepository;

    @Transactional
    public void incrementCounter(String fromCurrency, String toCurrency, LocalDate date) {
        Optional<UsageCounter> existingCounter = usageCounterRepository.findByFromCurrencyAndToCurrencyAndRequestDate(fromCurrency, toCurrency, date);

        if (existingCounter.isPresent()) {
            UsageCounter counter = existingCounter.get();
            counter.setCounter(counter.getCounter() + 1);
            usageCounterRepository.save(counter);
        } else {
            UsageCounter newCounter = new UsageCounter();
            newCounter.setFromCurrency(fromCurrency);
            newCounter.setToCurrency(toCurrency);
            newCounter.setRequestDate(date);
            newCounter.setCounter(1);
            usageCounterRepository.save(newCounter);
        }
    }


}
