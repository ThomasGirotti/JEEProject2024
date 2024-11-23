package com.jeemudae.collection.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeemudae.collection.repository.Character;
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
    public String collection(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        List<Character> characters = characterService.getCharactersForUser(user);
        model.addAttribute("characters", characters);
        return "collection";
    }

    @PostMapping("/collection/add")
    public String addCharacter(@RequestParam("name") String name, @RequestParam("price") Short price, @RequestParam("image") String image, @RequestParam("claimCount") Short claimCount, @RequestParam("likeCount") Short likeCount) {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Créer un nouveau personnage
        Character character = new Character();
        character.setName(name);
        character.setPrice(price);
        character.setImageUrl(image);
        character.setClaimCount(claimCount);
        character.setLikeCount(likeCount);
        character.setUser(user);

        // Sauvegarder le personnage
        characterService.saveCharacter(character);

        // Rediriger vers la page collection
        return "redirect:/collection";
    }
}
