package com.example.controllers;

import com.example.services.AdminCheckService;
import com.example.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InitAdminController {
    private final UserService userService;
    private final AdminCheckService adminCheckService;

    public InitAdminController(UserService userService, AdminCheckService adminCheckService) {
        this.userService = userService;
        this.adminCheckService = adminCheckService;
    }

    @GetMapping("/init-admin")
    public String initAdmin(Model model) {
        if(adminCheckService.isAdminInitialized()) {
            return "redirect:/login";
        }
        model.addAttribute("view", "init");
        model.addAttribute("element", "admin-form");
        return "layout";
    }

    @PostMapping("/init-admin")
    public String initAdmin(@RequestParam String username, @RequestParam String password) {
        userService.createAdmin(username, password);
        return "redirect:/login";
    }
}
