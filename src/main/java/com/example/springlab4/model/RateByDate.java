package com.example.springlab4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
public class RateByDate {
    private LocalDate date;
    private HashSet<Rate> currencies;

    public RateByDate() {
        this.currencies = new HashSet<>();
    }

    public void addCurrency(Rate rate) {
        this.currencies.add(rate);
    }

    public void removeCurrency(String currencyName) {
        this.currencies.removeIf(rate -> rate.getCurrency().getCurrencyCode().equals(currencyName));
    }
}
