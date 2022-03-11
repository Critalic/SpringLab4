package com.example.springlab4.dao;

import com.example.springlab4.model.Rate;
import com.example.springlab4.model.RateByDate;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainDaoImpl implements MainDao {
    private final ArrayList<RateByDate> rateByDates;

    public MainDaoImpl(ArrayList<RateByDate> rateByDates) {
        this.rateByDates = rateByDates;
    }

    public ArrayList<RateByDate> getRates() {
        return null;
    }
    public synchronized void addCurrency(Rate rate, LocalDate date) {}
    public synchronized void deleteCurrency(String currencyCode, LocalDate date) {}
    public synchronized void deleteRateByDate(LocalDate date) {}

    public synchronized void addRateByDate(RateByDate rateByDate) {}
}
