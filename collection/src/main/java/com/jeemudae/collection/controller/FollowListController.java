package com.jeemudae.collection.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;

@Controller
public class FollowListController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/followList")
    public String getFollowList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
            .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        Set<User> following = user.getFollowing();
        model.addAttribute("following", following);
        return "followList";
    }
}
