package com.jeemudae.collection.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeemudae.collection.repository.Character;
import com.jeemudae.collection.repository.Trade;
import com.jeemudae.collection.repository.TradeRepository;
import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;
import com.jeemudae.collection.service.CharacterService;
import com.jeemudae.collection.service.TradeService;

@Controller
public class TradeController {
    
    @Autowired
    private TradeService tradeService;
    
    @Autowired
    private CharacterService characterService;
    
    @Autowired
    private TradeRepository tradeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/createTrade")
    public String getCreateTradePage(@RequestParam(value = "characterId", required = false) Long characterId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        List<Character> charactersInTrade = characterService.getCharactersInTrade();
        model.addAttribute("charactersInTrade", charactersInTrade);
        List<Character> userCharacters = currentUser.getCollectionSet().getCharacters();
        List<Character> availableCharacters = userCharacters.stream().filter(character -> !character.isInTrade()).collect(Collectors.toList());
        model.addAttribute("characters", availableCharacters); 
        return "createTrade"; 
    }
    
    @PostMapping("/createTrade")
    public String proposeTrade(@RequestParam("characterId") Long characterId) {
        tradeService.setCharacterInTrade(characterId, true);
        return "redirect:/createTrade"; 
    }
    
    @PostMapping("/proposerTrade")
    public String proposeTrade(@RequestParam("characterId") Long tradeCharacterId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        List<Character> userCharacters = currentUser.getCollectionSet().getCharacters();
        Character tradeCharacter = characterService.getCharacterById(tradeCharacterId);
        model.addAttribute("tradeCharacter", tradeCharacter);
        model.addAttribute("userCharacters", userCharacters);
        return "proposerTrade";
    }
    
    @PostMapping("/submitTradeOffer")
    public String submitTradeOffer(
    @RequestParam("tradeCharacterId") Long tradeCharacterId,
    @RequestParam(value = "selectedCharacters", required = false) List<Long> selectedCharacterIds,
    Model model) {
        if (selectedCharacterIds == null || selectedCharacterIds.isEmpty()) {
            model.addAttribute("message", "Veuillez sélectionner au moins un personnage à proposer.");
            return "createTrade";
        }
        System.out.println("Trade Character ID: " + tradeCharacterId);
        System.out.println("Selected Characters IDs: " + selectedCharacterIds);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        Character tradeCharacter = characterService.getCharacterById(tradeCharacterId);
        List<Character> offeredCharacters = characterService.getCharactersByIds(selectedCharacterIds);
        tradeService.createTradeOffer(tradeCharacter, offeredCharacters, currentUser);
        model.addAttribute("message", "Votre offre a été envoyée !");
        return "redirect:/createTrade";
    }
    
    @GetMapping("/propositionsTrade")
    public String afficherPropositionsTrade(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        List<Trade> propositions = tradeRepository.findByUser(currentUser);
        for (Trade trade : propositions) {
            System.out.println("Trade ID: " + trade.getId());
            System.out.println("Trade Character: " + trade.getTradeCharacter().getName());
            System.out.println("Offered Characters: ");
            for (Character character : trade.getOfferedCharacters()) {
                System.out.println(" - " + character.getName());
            }
        }
        if (propositions.isEmpty()) {
            System.out.println("Aucune proposition de trade trouvée.");
        }
        model.addAttribute("propositions", propositions);
        return "propositionsTrade";
    }
    
    @PostMapping("/accepterTrade")
    public String accepterTrade(@RequestParam Long tradeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        return "redirect:/propositionsTrade";
    }
    
    @PostMapping("/refuserTrade")
    public String refuserTrade(@RequestParam Long tradeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        return "redirect:/propositionsTrade";
    }
}
