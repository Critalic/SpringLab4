package com.example.springlab4;

import com.example.springlab4.dao.MainDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

@SpringBootApplication
public class SpringLab4Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringLab4Application.class, args);
        context.getBean(MainDao.class).getRates();
    }
}
