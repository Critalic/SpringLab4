package com.example.springlab4.service;

import com.example.springlab4.dao.MainRepository;
import com.example.springlab4.model.Rate;
import com.example.springlab4.model.RateByDate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.security.InvalidParameterException;
import java.sql.BatchUpdateException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MainService {
    private ApplicationContext context;
    private final MainRepository mainRepository;

    public MainService(@Qualifier("mock") MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public void addRateByDate(String date, Rate ...rates) throws BatchUpdateException {
        RateByDate rateByDate = new RateByDate();
        rateByDate.setDate(parseDate(date));
        for (Rate rate: rates) {
            if(rateByDate.getCurrencies().contains(rate)) {
                throw new IllegalArgumentException("Duplicate currency");
            }
            rateByDate.addCurrency(rate);
        }
        mainRepository.addRateByDate(rateByDate);
    }

    public RateByDate getTodayRate() {
        return mainRepository.getRates().stream()
                .max(new CompareRate())
                .orElse(new RateByDate());
    }

    public List<RateByDate> getSpecifiedRates(LocalDate from, LocalDate to) {
        return mainRepository.getRates().stream()
                .filter(rate -> rate.getDate().isAfter(from) &&
                        rate.getDate().isBefore(to))
                .collect(Collectors.toList());
    }

    public String validateCurrencyCode(String currCode) {
        if (Currency.getAvailableCurrencies().stream().noneMatch(curr -> curr.getCurrencyCode().equals(currCode))) {
            throw new InvalidParameterException("Invalid currency code argument");
        }
        return currCode;
    }

    public RateByDate getSpecifiedRate(LocalDate on) {
        return mainRepository.getRates().stream()
                .filter(rate -> rate.getDate().equals(on))
                .findFirst().orElseThrow(() -> new NotFoundException("Object not found"));
    }

    public LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("Invalid date argument");
        }
    }

    public void deleteCurrency(String rateName, LocalDate date) throws BatchUpdateException {
        mainRepository.deleteCurrency(rateName, date);
    }

    public void deleteRateByDate(LocalDate date) throws BatchUpdateException {
        mainRepository.deleteRateByDate(date);
    }

    public void addCurrencyRate(String currencyCode, double inputRate, LocalDate date) {
        mainRepository.addCurrency(new Rate(currencyCode, inputRate), date);
    }

    private RateByDate getMostRecentRate() {
        ArrayList<RateByDate> rates = mainRepository.getRates();
        rates.sort(new CompareRate());
        return rates.get(0);
    }

    public boolean isAdmin(String input) {
        return input.equals("admin");
    }

    static class CompareRate implements Comparator<RateByDate> {

        @Override
        public int compare(RateByDate o1, RateByDate o2) {
            if (o1.getDate().isAfter(o2.getDate())) return 1;
            else if (o1.getDate().isBefore(o2.getDate())) return -1;
            return 0;
        }
    }
}
