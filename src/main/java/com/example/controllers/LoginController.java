package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("view", "login");
        model.addAttribute("element", "content");
        return "layout";
    }

    @GetMapping("/")
    public String home(Principal principal, Model model) {
        String username = principal != null ? principal.getName() : "Guest";
        model.addAttribute("user", username);
        model.addAttribute("title", "Welcome, " + username + "!");
        model.addAttribute("view", "home");
        model.addAttribute("element", "content");
        return "layout";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
