package com.example.springlab4.controllers;

import com.example.springlab4.service.MainService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class RstController {
    private final MainService mainService;
    public RstController(MainService mainService) {
        this.mainService = mainService;
    }

//    @DeleteMapping()
}
