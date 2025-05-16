package com.example.society.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {

    @GetMapping("/admin/login")
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping("/admin/dashboard")
    public String dashboardPage() {
        return "admin/dashboard";
    }
}
