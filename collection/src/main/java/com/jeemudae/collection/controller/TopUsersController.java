package com.jeemudae.collection.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.service.UserService;

@Controller
public class TopUsersController {

    private final UserService userService;

    public TopUsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/topusers")
    public String getTopUsers(Model model) {
        List<User> topUsers = userService.getTopUsersByCollectionValue();
        model.addAttribute("topUsers", topUsers);
        return "topusers"; 
    }
}
