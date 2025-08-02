package com.example.controllers;

import com.example.initializers.AdminInitializationChecker;
import com.example.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InitAdminController {
    private final UserService userService;
    private final AdminInitializationChecker adminInitializationChecker;
    public InitAdminController(UserService userService, AdminInitializationChecker adminInitializationChecker) {
        this.userService = userService;
        this.adminInitializationChecker = adminInitializationChecker;
    }

    @GetMapping("/init-admin")
    public String initAdmin(Model model) {
        if(adminInitializationChecker.adminExists()) {
            return "redirect:/init-reboot";
        }
        model.addAttribute("view", "init");
        model.addAttribute("element", "admin-form");
        return "layout";
    }

    @PostMapping("/init-admin")
    public String initAdmin(Model model, @RequestParam String username, @RequestParam String password) {
        userService.createAdmin(username, password);
        return "redirect:/init-reboot";
    }

    @GetMapping("/init-reboot")
    public String requestReboot(Model model) {
        if(!adminInitializationChecker.adminExists()) {
            return "redirect:/init-admin";
        }
        model.addAttribute("view", "init");
        model.addAttribute("element", "reboot-warn");
        return "layout";
    }
}
