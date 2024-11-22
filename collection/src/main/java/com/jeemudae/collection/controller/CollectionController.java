package com.jeemudae.collection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.service.CharacterService;

@Controller
public class CollectionController {
    @Autowired
    private CharacterService characterService;

    @GetMapping("/collection")
    public String collection(Model model) {
        List<Character> characters = characterService.getAllCharacters();
        model.addAttribute("characters", characters);
        return "collection";
    }
}
