package com.example.springlab4.model;

import java.util.Currency;
import java.util.Objects;

public class Rate {
    private Currency currency;
    private double courseToUAH;

    public Rate(String currency, double courseToUAH) {
        this.currency = Currency.getInstance(currency);
        this.courseToUAH = courseToUAH;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setCourseToUAH(double courseToUAH) {
        this.courseToUAH = courseToUAH;
    }

    public double getCourseToUAH() {
        return courseToUAH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate1 = (Rate) o;
        return currency.equals(rate1.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency);
    }
}


