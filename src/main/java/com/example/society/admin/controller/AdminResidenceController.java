package com.example.society.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping; // ✅ Important
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/residences")
public class AdminResidenceController {

    @GetMapping
    public String showResidenceListingPage() {
        return "admin/includes/residence-list"; 

    }
}
