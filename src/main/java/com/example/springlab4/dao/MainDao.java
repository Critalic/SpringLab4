package com.example.springlab4.dao;

import com.example.springlab4.model.Rate;
import com.example.springlab4.model.RateByDate;

import java.time.LocalDate;
import java.util.ArrayList;

public interface MainDao {
    ArrayList<RateByDate> getRates();

    void addCurrency(Rate rate, LocalDate date);

    void deleteCurrency(String currencyCode, LocalDate date);

    void deleteRateByDate(LocalDate date);

    void addRateByDate(RateByDate rateByDate);
}
