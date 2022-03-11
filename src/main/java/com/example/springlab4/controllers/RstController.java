package com.example.springlab4.controllers;

import com.example.springlab4.model.Rate;
import com.example.springlab4.model.RateByDate;
import com.example.springlab4.service.MainService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.security.InvalidParameterException;
import java.sql.BatchUpdateException;
import java.time.format.DateTimeParseException;

@RestController(value = "/api")
public class RstController {
    private final MainService mainService;

    public RstController(MainService mainService) {
        this.mainService = mainService;
    }

    @DeleteMapping("/rates/{date}/currency/{code}")
    public ResponseEntity<String> deleteCurrency(@PathVariable String date, @PathVariable String code) {
        try {
            mainService.deleteEntry(mainService.validateCurrencyCode(code), mainService.parseDate(date));
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (BatchUpdateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Specified object doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/rates/{date}/currency/{code}")
    public ResponseEntity<String> addCurrency(
            @PathVariable String date, @PathVariable String code, @RequestParam double rate) {
        try {
            mainService.addCurrencyRate(mainService.validateCurrencyCode(code), rate, mainService.parseDate(date));
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/rates/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSpecifiedRate(@PathVariable String date) { //TODO: analyze <?> use
        try {
            return new ResponseEntity<>(
                    mainService.getSpecifiedRate(mainService.parseDate(date)), HttpStatus.OK);
        } catch (InvalidParameterException | NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value = "/rates")
    public ResponseEntity<String> addRateByDate(@RequestParam String date, @RequestBody Rate ...rates) {
        mainService.addRateByDate(date, rates);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
