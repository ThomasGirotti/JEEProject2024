package com.jeemudae.collection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.service.CustomUserDetailsService;

@Controller
public class AuthController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {
        userDetailsService.saveUser(user);
        return "redirect:/login?registered";
    }
}
