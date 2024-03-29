package com.example.springlab4.controllers;

import com.example.springlab4.service.MainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller("/main")
public class MainController {
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping()
    ResponseEntity<String> read() {
        if (new Random().nextInt() < 10) {
            return ResponseEntity
                    .status(HttpStatus.I_AM_A_TEAPOT)
                    .body("Random is less than 10");
//            return ResponseEntity.ok("Random is less than 10");
        }
        return ResponseEntity.ok("Random is bigger than 10");
    }

    @PostMapping("/view")
    @ResponseBody
    public String viewCurrencies(HttpSession session,
                                 Model model,
                                 @RequestParam("role") String role) {
//        session.setAttribute("isAdmin", mainService.isAdmin(role));
//        model.addAttribute("role", role);
//        RateByDate todayRate = mainService.getTodayRate();
//        model.addAttribute("date", todayRate.getDate());
//        model.addAttribute("currencies", todayRate.getCurrencies());
        return "currenciesView";
    }

//    @GetMapping("/view")
//    public String viewCurrencies(Model model) {
//        RateByDate todayRate = mainService.getTodayRate();
//        model.addAttribute("date", todayRate.getDate());
//        model.addAttribute("currencies", todayRate.getCurrencies());
//        return "currenciesView";
//    }
//
//
//    @GetMapping("/searchRates")
//    public String searchRates(Model model,
//                              @RequestParam("from") String from,
//                              @RequestParam("to") String to) throws ParseException {
//        LocalDate fromDate = mainService.parseDate(from);
//        LocalDate toDate = mainService.parseDate(to);
//        List<RateByDate> rates = mainService.getSpecifiedRates(fromDate, toDate);
//        model.addAttribute("rates", rates);
//        return "searchedCurrencies";
//    }
}
