package com.jeemudae.collection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.service.UserService;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String adminHome(Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (!user.isAdmin()) {
            return "redirect:/";
        }
        return "admin/admin";
    }
}
