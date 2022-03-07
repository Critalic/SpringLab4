package com.example.springlab4.service;

import com.example.springlab4.dao.MainDao;
import com.example.springlab4.model.Rate;
import com.example.springlab4.model.RateByDate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainService {
    private ApplicationContext context;
    private final MainDao mainDao;

    public MainService(MainDao mainDao) {
        this.mainDao = mainDao;
    }

    public boolean isAdmin(String input) {
        return input.equals("admin");
    }

    public void addRate(LocalDate date, HashSet<Rate> currencies) {
        RateByDate rate = context.getBean(RateByDate.class);
        rate.setDate(date);
        rate.setCurrencies(currencies);
        mainDao.addRateByDate(rate);
    }

    public RateByDate getTodayRate() {
        return mainDao.getRates().stream()
                .max(new CompareRate())
                .orElse(new RateByDate());
    }

    public List<RateByDate> getSpecifiedRates(LocalDate from, LocalDate to) {
        return mainDao.getRates().stream()
                .filter(rate  -> rate.getDate().isAfter(from) &&
                        rate.getDate().isBefore(to))
                .collect(Collectors.toList());
    }

    public RateByDate getSpecifiedRate(LocalDate on) {
        return mainDao.getRates().stream()
                .filter(rate  -> rate.getDate().equals(on))
                .findFirst().orElse(new RateByDate());
    }

    public LocalDate parseDate(String date) throws ParseException {
        return LocalDate.parse(date);
    }

    public void deleteEntry(String rateName, LocalDate date) {
        mainDao.deleteCurrency(rateName, date);
    }

    public void addEntry(String inputName, double inputRate, LocalDate date){
        mainDao.addCurrency(new Rate(inputName, inputRate), date);
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
