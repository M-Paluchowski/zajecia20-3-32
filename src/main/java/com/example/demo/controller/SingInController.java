package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SingInController {

    private UserService userService;

    @Autowired
    public SingInController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    String main() {
        return "register";
    }

    @PostMapping("/register")
    String register(@RequestParam String username, @RequestParam String password) {
        userService.save(username, password);
        return "redirect:/login";
    }
}
