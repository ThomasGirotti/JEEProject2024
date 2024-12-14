package com.jeemudae.collection.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.service.UserService;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if (authentication != null) {
            User user = userService.getUserByUsername(authentication.getName());
            model.addAttribute("isAdmin", user.isAdmin());
        }
        return "home";
    }
}
