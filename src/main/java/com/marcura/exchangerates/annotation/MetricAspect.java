package com.marcura.exchangerates.annotation;

import com.marcura.exchangerates.service.UsageCounterService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Aspect
@Component
public class MetricAspect {

    @Autowired
    private UsageCounterService usageCounterService;

    @Around("@annotation(com.marcura.exchangerates.annotation.Metric)")
    public Object processMetric(ProceedingJoinPoint joinPoint) throws Throwable {
        Object response = joinPoint.proceed();

        if (joinPoint.getSignature().getName().equals("calculateExchangeRate")) {
            Object[] args = joinPoint.getArgs();
            String fromCurrency = (String) args[0];
            String toCurrency = (String) args[1];
            LocalDate requestDate = (LocalDate) args[2];

            usageCounterService.incrementCounter(fromCurrency, toCurrency, requestDate);
        }

        return response;
    }
}
