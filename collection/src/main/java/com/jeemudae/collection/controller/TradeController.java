package com.jeemudae.collection.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.service.CharacterService;
import com.jeemudae.collection.service.TradeService;
import com.jeemudae.collection.service.UserService;


@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CharacterService characterService;

    @GetMapping("/createTrade")
    public String getCreateTradePage(@RequestParam(value = "characterId", required = false) Long characterId, Model model) {
        List<Character> charactersInTrade = characterService.getCharactersInTrade();
    
        model.addAttribute("charactersInTrade", charactersInTrade);

        List<Character> characters = characterService.getAllCharacters();
        List<Character> availableCharacters = characters.stream()
            .filter(character -> !character.isInTrade()) 
            .collect(Collectors.toList());
        
        if (characterId != null) {
            model.addAttribute("selectedCharacterId", characterId);
        }

        model.addAttribute("characters", availableCharacters); 
        return "createTrade"; 
    }


@PostMapping("/proposeTrade")
public String proposeTrade(@RequestParam("characterId") Long characterId) {
    // Modifier l'état du personnage en "inTrade"
    tradeService.setCharacterInTrade(characterId, true);

    return "createTrade"; 
}


    @PostMapping("/addCharacterToTrade")
    public String addCharacterToTrade(@RequestParam("tradeId") Long tradeId, @RequestParam("characterId") Long characterId) {
        tradeService.addCharacterToTrade(tradeId, characterId);
        return "redirect:/tradeDetails?tradeId=" + tradeId;
    }
}
