package com.jeemudae.collection.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.service.CharacterService;

@Controller
public class CharacterController {
    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/topcharacters")
    public String getTopCharacters(Model model) {
        List<Character> characters = characterService.getAllCharactersSortedByPriceDesc();
        model.addAttribute("characters", characters);
        return "topcharacters";
    }
}
