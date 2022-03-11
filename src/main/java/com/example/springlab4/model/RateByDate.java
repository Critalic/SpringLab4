package com.example.springlab4.model;

import java.time.LocalDate;
import java.util.HashSet;

public class RateByDate {
    private LocalDate date;
    private HashSet<Rate> currencies;

    public RateByDate() {
        this.currencies = new HashSet<>();
    }

    public RateByDate(LocalDate date,  HashSet<Rate> currencies) {
        this.date = date;
        this.currencies = currencies;
    }

    public LocalDate getDate() {
        return date;
    }

    public  HashSet<Rate> getCurrencies() {
        return currencies;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCurrencies( HashSet<Rate> currencies) {
        this.currencies = currencies;
    }

    public void addCurrency(Rate rate) {
        this.currencies.add(rate);
    }

    public void removeCurrency(String currencyName) {
        this.currencies.removeIf(rate -> rate.getCurrency().getCurrencyCode().equals(currencyName));
    }
}
