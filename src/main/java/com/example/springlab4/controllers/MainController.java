package com.example.springlab4.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController("/main")
public class MainController {

    @GetMapping()
    ResponseEntity<String> read() {
        if(new Random().nextInt()<10) {
            return ResponseEntity
                    .status(HttpStatus.I_AM_A_TEAPOT)
                    .body("Random is less than 10");
//            return ResponseEntity.ok("Random is less than 10");
        }
        return ResponseEntity.ok("Random is bigger than 10");
    }
}
