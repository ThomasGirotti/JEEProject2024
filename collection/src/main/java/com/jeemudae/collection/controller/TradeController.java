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
import com.jeemudae.collection.service.UserService;

@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private UserRepository userRepository;

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


@PostMapping("/createTrade")
public String proposeTrade(@RequestParam("characterId") Long characterId) {
    // Modifier l'état du personnage en "inTrade"
    tradeService.setCharacterInTrade(characterId, true);

    return "createTrade"; 
}


    @PostMapping("/proposerTrade")
    public String proposeTrade(@RequestParam("characterId") Long tradeCharacterId, Model model) {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));

        // Récupérer les personnages de cet utilisateur
        List<Character> userCharacters = currentUser.getCollectionSet().getCharacters();

        // Récupérer le personnage à échanger
        Character tradeCharacter = characterService.getCharacterById(tradeCharacterId);

        

        // Ajouter les données au modèle
        model.addAttribute("tradeCharacter", tradeCharacter);
        model.addAttribute("userCharacters", userCharacters);

        return "proposerTrade"; // Retourne la page "proposerTrade.html"
    }


    @PostMapping("/submitTradeOffer")
    public String submitTradeOffer(
        @RequestParam("tradeCharacterId") Long tradeCharacterId,
        @RequestParam(value = "selectedCharacters", required = false) List<Long> selectedCharacterIds,
        Model model) {

        // Vérifier que des personnages ont été sélectionnés
        if (selectedCharacterIds == null || selectedCharacterIds.isEmpty()) {
            model.addAttribute("message", "Veuillez sélectionner au moins un personnage à proposer.");
            return "createTrade"; // Retourner à la page de création de trade avec un message d'erreur
        }

        System.out.println("Trade Character ID: " + tradeCharacterId);
    System.out.println("Selected Characters IDs: " + selectedCharacterIds);

        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));

        
                // Récupérer le personnage en trade
        Character tradeCharacter = characterService.getCharacterById(tradeCharacterId);

        // Récupérer les personnages proposés par l'utilisateur
        List<Character> offeredCharacters = characterService.getCharactersByIds(selectedCharacterIds);

        // Créer une nouvelle offre de trade avec l'utilisateur connecté
        tradeService.createTradeOffer(tradeCharacter, offeredCharacters, currentUser);

        // Ajouter un message de confirmation
        model.addAttribute("message", "Votre offre a été envoyée !");
        return "redirect:/createTrade"; // Retourne à la liste des trades
    }

    @GetMapping("/propositionsTrade")
    public String afficherPropositionsTrade(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));

        // Récupérer les trades où l'utilisateur est concerné
        List<Trade> propositions = tradeRepository.findByUser(currentUser); // Si vous préférez, vous pouvez utiliser 'findAllByUser'


        for (Trade trade : propositions) {
            System.out.println("Trade ID: " + trade.getId());
            System.out.println("Trade Character: " + trade.getTradeCharacter().getName());
            System.out.println("Offered Characters: ");
            for (Character character : trade.getOfferedCharacters()) {
                System.out.println(" - " + character.getName());
            }
        }
          

        List<Trade> propositionst = tradeRepository.findByUser(currentUser);

        if (propositions.isEmpty()) {
            System.out.println("Aucune proposition de trade trouvée.");
        }

        // Ajouter les propositions au modèle
        model.addAttribute("propositions", propositions);
        model.addAttribute("propositionst", propositionst);
        return "propositionsTrade";
    }


    
    @PostMapping("/accepterTrade")
    public String accepterTrade(@RequestParam Long tradeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));

        return "redirect:/propositionsTrade";
    }

    @PostMapping("/refuserTrade")
    public String refuserTrade(@RequestParam Long tradeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté non trouvé"));

        return "redirect:/propositionsTrade";
    }
}
