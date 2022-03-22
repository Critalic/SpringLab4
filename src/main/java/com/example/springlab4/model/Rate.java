package com.example.springlab4.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Currency;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Rate {
    private Currency currency;
    @EqualsAndHashCode.Exclude
    private double courseToUAH;

    public Rate(String currency, double courseToUAH) {
        this.currency = Currency.getInstance(currency);
        this.courseToUAH = courseToUAH;
    }
}


