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
import org.springframework.web.multipart.MultipartFile;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;
import com.jeemudae.collection.service.CharacterService;
import com.jeemudae.collection.service.FileStorageService;
@Controller
public class CollectionController {
    private final FileStorageService fileStorageService;
    private final CharacterService characterService;
    private final UserRepository userRepository;

    public CollectionController(FileStorageService fileStorageService, CharacterService characterService, UserRepository userRepository) {
        this.fileStorageService = fileStorageService;
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

    @PostMapping("/collection/add")
    public String addCharacter(@RequestParam("name") String name, @RequestParam("price") Short price, @RequestParam("image") MultipartFile image, @RequestParam("claimCount") Long claimCount, @RequestParam("likeCount") Long likeCount, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        String filename = null;
        if (image != null && !image.isEmpty()) {
            filename = fileStorageService.store(image); // Sauvegarder l'image
        }
        Character character = new Character();
        character.setName(name);
        character.setPrice(price);
        character.setImagePath(filename);
        character.setClaimCount(claimCount);
        character.setLikeCount(likeCount);
        character.setUser(currentUser);
        try {
            characterService.saveCharacter(character);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Un personnage avec ce nom existe déjà !");
            model.addAttribute("characters", characterService.getCharactersForUser(currentUser));
            return "collection";
        }
        return "redirect:/collection";
    }
}
