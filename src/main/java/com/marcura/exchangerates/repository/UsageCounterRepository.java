package com.marcura.exchangerates.repository;

import com.marcura.exchangerates.entity.UsageCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UsageCounterRepository extends JpaRepository<UsageCounter, Integer> {
    Optional<UsageCounter> findByFromCurrencyAndToCurrencyAndRequestDate(String fromCurrency, String toCurrency, LocalDate date);
}
