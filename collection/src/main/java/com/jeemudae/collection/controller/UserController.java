package com.jeemudae.collection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;
import com.jeemudae.collection.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/profile")
    public String viewProfile(@RequestParam(value = "username", required = false) String username, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userService.getUserByUsername(currentUsername);
        User userToDisplay;
        boolean following = false;
        if (username == null || username.equals(currentUsername)) {
            userToDisplay = currentUser;
        } else {
            userToDisplay = userService.getUserByUsername(username);
            following = userService.isUserFollowing(currentUsername, username);
        }
        model.addAttribute("user", userToDisplay);
        model.addAttribute("following", following);
        return "profile";
    }
    
    @PostMapping("/follow")
    public String followUser(@RequestParam("username") String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        userService.followUser(currentUsername, username);
        return "redirect:/profile?username=" + username;
    }
    
    @PostMapping("/unfollow")
    public String unfollowUser(@RequestParam("username") String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        userService.unfollowUser(currentUsername, username);
        return "redirect:/profile?username=" + username;
    }
    
    @GetMapping("/following")
    public String getFollowingList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User currentUser = userService.getUserByUsername(currentUsername);
        model.addAttribute("following", currentUser.getFollowing());
        return "following";
    }
}