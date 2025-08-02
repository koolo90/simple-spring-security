package com.example.controllers;

import com.example.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id).orElseThrow());
        model.addAttribute("roles", userService.getAllRoles());
        return "admin/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateUserRoles(@PathVariable Long id,
                                  @RequestParam(required = false) List<String> roles) {
        userService.updateUserRoles(id, new HashSet<>(roles != null ? roles : List.of()));
        return "redirect:/admin/users";
    }


}