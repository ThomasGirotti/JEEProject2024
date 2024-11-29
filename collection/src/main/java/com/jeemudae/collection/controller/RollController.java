package com.jeemudae.collection.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.service.CharacterService;
import com.jeemudae.collection.service.RollService;
import com.jeemudae.collection.service.UserService;

@Controller
@RequestMapping("/roll")
public class RollController {
    private final UserService userService;
    private final RollService rollService;
    private final CharacterService characterService;

    public RollController(UserService userService, RollService rollService, CharacterService characterService) {
        this.userService = userService;
        this.rollService = rollService;
        this.characterService = characterService;
    }

    @GetMapping
    public String rollPage(Model model, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (!userService.canRoll(user)) {
            model.addAttribute("error", "Vous devez attendre pour faire un roll.");
            return "roll";
        }
        List<Character> rolledCharacters = rollService.rollCharacters();
        userService.updateRollTime(user); // Met à jour l'heure de roll
        model.addAttribute("characters", rolledCharacters);
        return "roll";
    }

    @PostMapping("/claim")
    public String claimCharacter(@RequestParam("characterId") Long characterId, Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        if (!userService.canClaim(user)) {
            model.addAttribute("error", "Vous devez attendre pour claim un personnage.");
            return "roll";
        }
        Character character = characterService.getCharacterById(characterId);
        rollService.claimCharacter(user, character);
        userService.updateClaimTime(user); // Met à jour l'heure de claim
        return "redirect:/collection";
    }
}
