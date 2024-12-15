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
import com.jeemudae.collection.repository.CharacterRepository;
import com.jeemudae.collection.repository.Trade;
import com.jeemudae.collection.repository.TradeRepository;
import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;
import com.jeemudae.collection.service.CharacterService;
import com.jeemudae.collection.service.TradeService;

import jakarta.transaction.Transactional;

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
    
    @Autowired
    private CharacterRepository characterRepository;
    
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
    @RequestParam(value = "selectedCharacters", required = false) List<Long> selectedCharacterIds, Model model) {
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
    
    @GetMapping("/finalizeTrade")
    public String finalizeTrade(@RequestParam("currentUserId") Long currentUserId, @RequestParam("otherUserId") Long otherUserId) {
        characterService.updateCall(currentUserId);
        characterService.updateCall(otherUserId);
        return "redirect:/collection";
    }
    
    @GetMapping("/propositionsTrade")
    public String afficherPropositionsTrade(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        List<Trade> propositions = tradeRepository.findByUser(currentUser);
        if (propositions.isEmpty()) {
            System.out.println("Aucune proposition de trade trouvée.");
        }
        model.addAttribute("propositions", propositions);
        return "propositionsTrade";
    }
    
    @PostMapping("/accepterTrade")
    @Transactional
    public String accepterTrade(@RequestParam Long tradeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new RuntimeException("Trade introuvable"));
        Character tradeCharacter = trade.getTradeCharacter();
        Character offeredCharacters = trade.getOfferedCharacters().get(0);
        User otherUser = offeredCharacters.getCollectionSet().getUser();
        for (Character offeredCharacter : trade.getOfferedCharacters()) {
            offeredCharacter.setCollectionSet(currentUser.getCollectionSet());
            offeredCharacter.setTrade(null); 
            characterRepository.save(offeredCharacter); 
        }
        if (tradeCharacter != null) {
            System.out.println("Transférer le personnage du trade à l'utilisateur qui a initié le trade" + tradeCharacter.getName());
            tradeCharacter.setCollectionSet(otherUser.getCollectionSet());
            tradeCharacter.setTrade(null); 
            tradeCharacter.setInTrade(false);
            characterRepository.save(tradeCharacter);
        }
        tradeRepository.delete(trade);
        return "redirect:/finalizeTrade?currentUserId=" + currentUser.getId() + "&otherUserId=" + otherUser.getId();
    }
    
    @PostMapping("/refuserTrade")
    @Transactional
    public String refuserTrade(@RequestParam Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new RuntimeException("Trade introuvable : ID " + tradeId));
        for (Character offeredCharacter : trade.getOfferedCharacters()) {
            offeredCharacter.setTrade(null);
            characterRepository.save(offeredCharacter);
        }
        Character tradeCharacter = trade.getTradeCharacter();
        if (tradeCharacter != null) {
            tradeCharacter.setTrade(null);
            characterRepository.save(tradeCharacter);
        }
        tradeRepository.delete(trade);
        return "redirect:/propositionsTrade";
    }
}