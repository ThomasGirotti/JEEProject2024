package com.jeemudae.collection.controller;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;
import com.jeemudae.collection.service.CharacterService;
@Controller
public class CollectionController {
    private final CharacterService characterService;
    private final UserRepository userRepository;

    public CollectionController(CharacterService characterService, UserRepository userRepository) {
        this.characterService = characterService;
        this.userRepository = userRepository;
    }

    @GetMapping("/collection")
    public String getCollection(@RequestParam(value = "username", required = false) String username, Model model) {
        Optional<User> optionalUser;
        if (username == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            User user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
            optionalUser = Optional.of(user);
        } else {
            optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isEmpty()) {
                model.addAttribute("errorMessage", "Aucun utilisateur trouvé pour ce nom.");
                return "search";
            }
        }
        User user = optionalUser.get();
        model.addAttribute("user", user);
        model.addAttribute("characters", characterService.getCharactersForUser(user));
        return "collection";
    }
}
