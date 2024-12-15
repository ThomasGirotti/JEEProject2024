package com.jeemudae.collection.controller;

import java.util.Optional;

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
import com.jeemudae.collection.service.CharacterService;
@Controller
public class CollectionController {
    
    @Autowired
    private CharacterService characterService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/collection")
    public String getCollection(
    @RequestParam(value = "username", required = false) String username,
    @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
    Model model) {
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
        model.addAttribute("characters", characterService.getSortedCharactersForUser(user, sortBy));
        model.addAttribute("sortBy", sortBy);
        return "collection";
    }
    
    @PostMapping("/collection/sell")
    public String sellCharacter(@RequestParam("characterId") Long characterId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
        .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        characterService.sellCharacter(user, characterId);
        characterService.updateCall(user.getCollectionSet().getId());
        return "redirect:/collection";
    }

    @PostMapping("/collection/moveLeft")
    public String moveCharacterLeft(@RequestParam("characterId") Long characterId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
            .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        characterService.moveCharacterLeft(user, characterId);
        return "redirect:/collection?sortBy=custom";
    }

    @PostMapping("/collection/moveRight")
    public String moveCharacterRight(@RequestParam("characterId") Long characterId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
            .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        characterService.moveCharacterRight(user, characterId);
        return "redirect:/collection?sortBy=custom";
    }
}
