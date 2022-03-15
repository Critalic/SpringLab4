package com.example.springlab4.configuration;

import com.example.springlab4.dao.MainRepository;
import com.example.springlab4.dao.MainRepositoryImpl;
import com.example.springlab4.model.Rate;
import com.example.springlab4.model.RateByDate;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.BatchUpdateException;
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

    @Bean("mock")
    public MainRepository mainDaoMock() {
        MainRepository mainRepository = Mockito.mock(MainRepository.class);

        Mockito.when(mainRepository.getRates()).thenReturn(rates);

        Mockito.doAnswer(invocationOnMock -> {
            LocalDate localDate = invocationOnMock.getArgument(1, LocalDate.class);
            if (rates.stream().anyMatch(rateByDate -> rateByDate.getDate().equals(localDate))) {
                return true;
            }
            throw new BatchUpdateException();
        }).when(mainRepository).addCurrency(any(Rate.class), any(LocalDate.class));

        Mockito.doAnswer(invocationOnMock -> {
            String curr = invocationOnMock.getArgument(0, String.class);
            LocalDate localDate = invocationOnMock.getArgument(1, LocalDate.class);

            if (rates.stream()
                    .filter(rateByDate -> rateByDate.getDate().equals(localDate))
                    .anyMatch(rateByDate -> rateByDate.getCurrencies().stream()
                            .anyMatch(currency -> currency.getCurrency().getCurrencyCode().equals(curr)))) {
                return true;
            }
            throw new BatchUpdateException();
        }).when(mainRepository).deleteCurrency(anyString(), any(LocalDate.class));

        Mockito.doAnswer(invocationOnMock -> {
            RateByDate rateByDate = invocationOnMock.getArgument(0, RateByDate.class);

            if (rates.stream()
                    .anyMatch(rate -> rate.getDate().equals(rateByDate.getDate()))) {
                throw new BatchUpdateException();
            }
            return true;
        }).when(mainRepository).addRateByDate(any(RateByDate.class));

        Mockito.doAnswer(invocationOnMock -> {
        LocalDate date = invocationOnMock.getArgument(0, LocalDate.class);
            if (rates.stream()
                    .noneMatch(rate -> rate.getDate().equals(date))) {
                throw new BatchUpdateException();
            }
            return true;
        }).when(mainRepository).deleteRateByDate(any(LocalDate.class));

        return mainRepository;
    }

    @Bean("impl")
    public MainRepository mainDaoImpl() {
       return new MainRepositoryImpl(rates);
    }

    private ArrayList<RateByDate> getRateByDate() {
        ArrayList<RateByDate> rates = new ArrayList<>();
        HashSet<Rate> currencies1 = new HashSet<>();
        currencies1.add(new Rate("USD", 28.7));
        currencies1.add(new Rate("EUR", 32.7));
        currencies1.add(new Rate("GBP", 39.7));

        HashSet<Rate> currencies2 = new HashSet<>();
        currencies2.add(new Rate("USD", 27.7));
        currencies2.add(new Rate("EUR", 31.7));
        currencies2.add(new Rate("GBP", 36.7));

        HashSet<Rate> currencies3 = new HashSet<>();
        currencies3.add(new Rate("USD", 27.2));
        currencies3.add(new Rate("EUR", 31.2));
        currencies3.add(new Rate("GBP", 36.3));

        rates.add(new RateByDate(
                LocalDate.of(2022, Month.JANUARY, 10), currencies1));
        rates.add(new RateByDate(
                LocalDate.of(2022, Month.JANUARY, 23), currencies2));
        rates.add(new RateByDate(
                LocalDate.of(2021, Month.DECEMBER, 23), currencies3));

        return rates;
    }
}
