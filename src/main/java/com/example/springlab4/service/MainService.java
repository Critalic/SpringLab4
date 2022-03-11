package com.example.springlab4.service;

import com.example.springlab4.dao.MainDao;
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
    private final MainDao mainDao;

    public MainService(@Qualifier("mock") MainDao mainDao) {
        this.mainDao = mainDao;
    }

    public boolean isAdmin(String input) {
        return input.equals("admin");
    }

    public void addRateByDate(String date, Rate ...rates) {
        RateByDate rateByDate = new RateByDate();
        rateByDate.setDate(parseDate(date));
        for (Rate rate: rates) {
            rateByDate.addCurrency(rate);
        }
        mainDao.addRateByDate(rateByDate);
    }

    public void addRateByDate(RateByDate rate) {
        mainDao.addRateByDate(rate);
    }

    public RateByDate getTodayRate() {
        return mainDao.getRates().stream()
                .max(new CompareRate())
                .orElse(new RateByDate());
    }

    public String validateCurrencyCode(String currCode) {
        if (Currency.getAvailableCurrencies().stream().noneMatch(curr -> curr.getCurrencyCode().equals(currCode))) {
            throw new InvalidParameterException("Invalid currency code argument");
        }
        return currCode;
    }

    public List<RateByDate> getSpecifiedRates(LocalDate from, LocalDate to) {
        return mainDao.getRates().stream()
                .filter(rate -> rate.getDate().isAfter(from) &&
                        rate.getDate().isBefore(to))
                .collect(Collectors.toList());
    }

    public RateByDate getSpecifiedRate(LocalDate on) {
        return mainDao.getRates().stream()
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

    public void deleteEntry(String rateName, LocalDate date) throws BatchUpdateException {
        mainDao.deleteCurrency(rateName, date);
    }

    public void addCurrencyRate(String currencyCode, double inputRate, LocalDate date) {
        mainDao.addCurrency(new Rate(currencyCode, inputRate), date);
    }

    private RateByDate getMostRecentRate() {
        ArrayList<RateByDate> rates = mainDao.getRates();
        rates.sort(new CompareRate());
        return rates.get(0);
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
