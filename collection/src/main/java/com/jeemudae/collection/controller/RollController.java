package com.jeemudae.collection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;
import com.jeemudae.collection.service.CharacterService;
import com.jeemudae.collection.service.RollService;
import com.jeemudae.collection.service.UserService;

@Controller
@RequestMapping("/roll")
public class RollController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RollService rollService;
    
    @Autowired
    private CharacterService characterService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public String showRollPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername).get();
        List<Character> characters = user.getRolledCharacters();
        if (characters.isEmpty()) {
            model.addAttribute("error", "Vous n'avez jamais effectué de rolls.");
        } else {
            model.addAttribute("characters", characters);
            model.addAttribute("user", user);
        }
        return "roll";
    }
    
    @PostMapping
    public String rollPage(Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (userService.canRoll(user)) {
            List<Character> rolledCharacters = rollService.rollCharacters();
            user.setRolledCharacters(rolledCharacters);
            userService.updateRollTime(user);
            userRepository.save(user);
            System.out.println("Roll effectué et enregistré pour " + user.getUsername());
            model.addAttribute("characters", rolledCharacters);
        } else {
            model.addAttribute("characters", user.getRolledCharacters());
            model.addAttribute("error", "Vous devez attendre pour faire un roll.");
        }
        model.addAttribute("user", user);
        return "roll";
    }
    
    @PostMapping("/claim")
    public String claimCharacter(@RequestParam("characterId") Long characterId, Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        Character character = characterService.getCharacterById(characterId);
        if (character.getCollectionSet() == null) {
            if (userService.canClaim(user)) {
                rollService.claimCharacter(user, character);
                model.addAttribute("success", "Personnage claim avec succès.");
                return "redirect:/collection";
            } else {
                model.addAttribute("error", "Vous n'avez plus de claim disponible.");
                return "roll";
            }
        } else {
            if (character.getCollectionSet().getUser().getId().equals(user.getId())) {
                if (userService.canBoost(user)) {
                    rollService.boostCharacter(user, character);
                    model.addAttribute("success", "Personnage boosté avec succès.");
                    return "redirect:/collection";
                } else {
                    model.addAttribute("error", "Vous n'avez plus de boost disponible.");
                    return "roll";
                }
            } else {
                model.addAttribute("error", "Ce personnage a déjà été claim par un autre utilisateur.");
                return "roll";
            }
        }
    }
}
