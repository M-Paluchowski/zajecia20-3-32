package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BankController {

    @PostMapping("/przelew")
    @ResponseBody
    String sendMoney(@RequestParam String to, @RequestParam Integer amount) {
        return "You sent " + amount + " $ to: " + to;
    }
}
