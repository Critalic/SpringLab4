package com.example.springlab4.configuration;

import com.example.springlab4.dao.MainDao;
import com.example.springlab4.model.Rate;
import com.example.springlab4.model.RateByDate;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Configuration
public class BeanConfig {
    private final ArrayList<RateByDate> rates;

    public BeanConfig() {
        this.rates = this.getRateByDate();
    }

    @Bean
    public MainDao mainDao () {
        MainDao mainDao = Mockito.mock(MainDao.class);

        Mockito.when(mainDao.getRates()).thenReturn(rates);
        Mockito.doNothing().when(mainDao).add
        Mockito.doAnswer().when(mainDao.addCurrency(any(Rate.class), any(LocalDate.class))).then();
        return mainDao;
    }

    private ArrayList<RateByDate> getRateByDate() {
        ArrayList<RateByDate> rates = new ArrayList<>();
        HashSet<Rate> currencies1 = new HashSet<>();
        currencies1.add(new Rate("USD", 28.7));
        currencies1.add(new Rate("EUR", 32.7));
        currencies1.add(new Rate("GBP", 38.7));

        HashSet<Rate> currencies2 = new HashSet<>();
        currencies1.add(new Rate("USD", 27.7));
        currencies1.add(new Rate("EUR", 31.7));
        currencies1.add(new Rate("GBP", 36.7));

        HashSet<Rate> currencies3 = new HashSet<>();
        currencies1.add(new Rate("USD", 27.2));
        currencies1.add(new Rate("EUR", 31.2));
        currencies1.add(new Rate("GBP", 36.3));

        rates.add(new RateByDate(
                LocalDate.of(2022, Month.JANUARY, 10), currencies1));
        rates.add(new RateByDate(
                LocalDate.of(2022, Month.JANUARY, 23), currencies2));
        rates.add(new RateByDate(
                LocalDate.of(2021, Month.DECEMBER, 23), currencies3));

        return rates;
    }
}
