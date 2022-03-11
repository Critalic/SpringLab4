package com.example.springlab4;

import com.example.springlab4.dao.MainDaoImpl;
import com.example.springlab4.model.Rate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
public class SpringLab4Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringLab4Application.class, args);
        context.getBean(MainDaoImpl.class).getRates();
        context.getBean(MainDaoImpl.class).addCurrency(new Rate("GBP", 38.7), LocalDate.of(2022, Month.JANUARY, 10));
        context.getBean(MainDaoImpl.class).addCurrency(new Rate("UAH", 38.7), LocalDate.of(2022, Month.JANUARY, 10));
    }
}
