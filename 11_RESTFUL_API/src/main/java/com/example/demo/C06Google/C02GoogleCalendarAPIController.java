package com.example.demo.C06Google;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/google/cal")
public class C02GoogleCalendarAPIController {
    @GetMapping
    public void get(){
        log.info("GET /google/cal...");
    }
}
