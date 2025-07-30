package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";  // plik: src/main/resources/templates/login.html
    }

    @GetMapping("/")
    public String home(Principal principal, Model model) {
        model.addAttribute("user", principal.getName());
        return "home";   // strona domowa po zalogowaniu
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";  // dostęp tylko dla ADMIN
    }
}
