package com.jeemudae.collection.controller;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
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
    public String getCollection(@RequestParam(value = "username", required = false) String username, Model model) {
        Optional<User> optionalUser;
        if (username == null) {
            // Si aucun username n'est passé, on récupère l'utilisateur connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            User user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
            optionalUser = Optional.of(user);
        } else {
            // Sinon, on récupère l'utilisateur spécifié par le paramètre
            optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isEmpty()) {
                model.addAttribute("errorMessage", "Aucun utilisateur trouvé pour ce nom.");
                return "search"; // Renvoie à la page de recherche
            }
        }
        User user = optionalUser.get();
        model.addAttribute("user", user);
        model.addAttribute("characters", characterService.getCharactersForUser(user));
        return "collection";
    }

    @PostMapping("/collection/add")
    public String addCharacter(@RequestParam("name") String name, @RequestParam("price") Short price, @RequestParam("image") String image, @RequestParam("claimCount") Long claimCount, @RequestParam("likeCount") Long likeCount, Model model) {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        // Créer un nouveau personnage
        Character character = new Character();
        character.setName(name);
        character.setPrice(price);
        character.setImageUrl(image);
        character.setClaimCount(claimCount);
        character.setLikeCount(likeCount);
        character.setUser(user);
        try {
            // Sauvegarder le personnage
            characterService.saveCharacter(character);
        } catch (DataIntegrityViolationException e) {
            // Ajouter un message d'erreur au modèle
            model.addAttribute("error", "Un personnage avec ce nom existe déjà !");
            // Retourner à la page collection avec le message d'erreur
            model.addAttribute("characters", characterService.getCharactersForUser(user));
            return "collection";
        }
        // Rediriger vers la page collection
        return "redirect:/collection";
    }
}
